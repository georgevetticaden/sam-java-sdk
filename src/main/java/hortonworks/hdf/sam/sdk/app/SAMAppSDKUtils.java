package hortonworks.hdf.sam.sdk.app;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.app.model.SAMApplication;
import hortonworks.hdf.sam.sdk.app.model.SAMApplicationStatus;
import hortonworks.hdf.sam.sdk.environment.SAMEnvironmentSDKUtils;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironment;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ResourceUtils;

public class SAMAppSDKUtils extends BaseSDKUtils {

	private SAMEnvironmentSDKUtils samEnvSDKUtils;

	public SAMAppSDKUtils(String restUrl) {
		super(restUrl);
		this.samEnvSDKUtils = new SAMEnvironmentSDKUtils(restUrl);
	}
	

	
	/**
	 * Gets the details of a SAM App given the id
	 * @param appId
	 * @return
	 */
	public SAMApplication getSAMAppDetails(Integer appId) {

		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("appId", String.valueOf(appId));
		String url = constructRESTUrl("/catalog/topologies/{appId}");		
		ResponseEntity<SAMApplication> response = restTemplate.exchange(url, HttpMethod.GET, null,  SAMApplication.class);
		return response.getBody();
	}
	
	/**
	 * Returns all the SAM Apps
	 * @return
	 */
	public List<SAMApplication> getAllSAMApps() {
		String url = constructRESTUrl("/catalog/topologies");
		
		ResponseEntity<Map<String, List<SAMApplication>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SAMApplication>>>() {});
		return response.getBody().get("entities");
	
	}

	/**
	 * Returns the details of a SAM App given its app name
	 * @param appName
	 * @return
	 */
	public SAMApplication getSAMApp(String appName) {
		List<SAMApplication> samApps = getAllSAMApps();
		for(SAMApplication samApp: samApps) {
			if(appName.equals(samApp.getName())) {
				return samApp;
			}
		}
		return null;
	}
	


	public SAMApplication importSAMApp(String appName, String samEnvName,
			Resource samAppImportFileResource) {
		
		if(samAppImportFileResource == null || !samAppImportFileResource.exists()) {
			String errMsg = "Cannot load SAM application file";
			throw new RuntimeException(errMsg);
		}
		/* Look sam env id */
		SAMEnvironment samEnv = samEnvSDKUtils.getSAMEnvironment(samEnvName);
		
		/* Create request object */
		LinkedMultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
		requestMap.add("file", samAppImportFileResource);
		requestMap.add("topologyName", appName);		
		requestMap.add("namespaceId", samEnv.getId());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);		
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>(requestMap, headers);
		
		//execute request
		String url = constructRESTUrl("/catalog/topologies/actions/import");
		ResponseEntity<SAMApplication> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SAMApplication.class);
		
		return response.getBody();
		
	}
	

	public void deleteSAMApp(String appName) {
		SAMApplication appToDelete = getSAMApp(appName);
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("appId", appToDelete.getId().toString());
		
		String url = constructRESTUrl("/catalog/topologies/{appId}");
		
		restTemplate.exchange(url, HttpMethod.DELETE, null, SAMApplication.class, mapParams);

	}



	public SAMApplication deploySAMApp(String appName) {
		SAMApplication samApp = getSAMApp(appName);
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("appId", samApp.getId().toString());
		
		String url = constructRESTUrl("/catalog/topologies/{appId}/actions/deploy");
		ResponseEntity<SAMApplication> response = restTemplate.exchange(url, HttpMethod.POST, null, SAMApplication.class, mapParams);
		return response.getBody();
	}



	public SAMApplicationStatus getSAMAppStatus(String appName) {
		SAMApplication appToDelete = getSAMApp(appName);
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("appId", appToDelete.getId().toString());
		
		String url = constructRESTUrl("/catalog/topologies/{appId}/actions/status");
		ResponseEntity<SAMApplicationStatus> response = restTemplate.exchange(url, HttpMethod.GET, null, SAMApplicationStatus.class, mapParams);
		return response.getBody();
	}



	public SAMApplication killSAMApp(String appName) {
		SAMApplication appToDelete = getSAMApp(appName);
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("appId", appToDelete.getId().toString());
		
		String url = constructRESTUrl("/catalog/topologies/{appId}/actions/kill");
		ResponseEntity<SAMApplication> response = restTemplate.exchange(url, HttpMethod.POST, null, SAMApplication.class, mapParams);
		return response.getBody();
	}
	
	

}

package hortonworks.hdf.sam.sdk.app;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.app.model.SAMAppSource;
import hortonworks.hdf.sam.sdk.app.model.SAMApplication;
import hortonworks.hdf.sam.sdk.app.model.SAMApplicationStatus;
import hortonworks.hdf.sam.sdk.environment.SAMEnvironmentSDKUtils;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironment;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironmentDetails;

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
		SAMEnvironmentDetails samEnv = samEnvSDKUtils.getSAMEnvironment(samEnvName);
		
		/* Create request object */
		LinkedMultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
		requestMap.add("file", samAppImportFileResource);
		requestMap.add("topologyName", appName);		
		requestMap.add("namespaceId", samEnv.getNamespace().getId());
		
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
		if(appToDelete == null) {
			LOG.warn("App["+ appName + " doesn't exist so nothing to delete");
			return;
		}		
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
		if(appToDelete == null) {
			LOG.warn("App["+ appName + " doesn't exist so nothing to kill");
			return null;
		}
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("appId", appToDelete.getId().toString());
		
		/* See if it is deployed if not, there is nothing to kill */
		if(!isAppDeployed(appName)) {
			LOG.warn("SAM app ["+ appName +"] is not deployed. So nthing to kill");
			return null;
		}
		String url = constructRESTUrl("/catalog/topologies/{appId}/actions/kill");
		ResponseEntity<SAMApplication> response = restTemplate.exchange(url, HttpMethod.POST, null, SAMApplication.class, mapParams);
		return response.getBody();
	}
	
	public boolean isAppDeployed(String appName) {
		try {
			SAMApplicationStatus appStatus =  getSAMAppStatus(appName);
			if("ACTIVE".equals(appStatus.getStatus())) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			//if exception app is not deployed
			return false;
		}
		
		
	}


	/**
	 * Returns all the sources associated with teh SAM App
	 * @param appName
	 * @return
	 */
	public List<SAMAppSource> getSAMAppSources(String appName) {
		
		SAMApplication samApp = getSAMApp(appName);	
		if(samApp == null) {
			String errMsg = "App["+appName + "] doesn't exist";
			throw new RuntimeException(errMsg);
		}
		
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("appId", samApp.getId().toString());
		
		String url = constructRESTUrl("/catalog/topologies/{appId}/sources");
		ResponseEntity<Map<String, List<SAMAppSource>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SAMAppSource>>>() {}, mapParams);
		return response.getBody().get("entities");
		
	}



	public SAMAppSource getSAMAppSource(String appName, String sourceName) {
		
		SAMAppSource sourceToFind = null;
		List<SAMAppSource> appSources = getSAMAppSources(appName);
		for(SAMAppSource source: appSources ) {
			if(source.getName().equals(sourceName)) {
				sourceToFind = source;
				break;
			}
		}
		
		return sourceToFind;
	}
	


}

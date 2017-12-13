package hortonworks.hdf.sam.sdk.component;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.component.model.ComponentType;
import hortonworks.hdf.sam.sdk.component.model.SAMComponent;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.ResourceUtils;

/**
 * Java Client to work with SAM components including uploading custom sources, sinks, processors. 
 * @author gvetticaden
 *
 */
public class SAMSourceSinkComponentSDKUtils extends BaseSDKUtils {

	public SAMSourceSinkComponentSDKUtils(String restUrl) {
		super(restUrl);
	}


	/**
	 * Uploads a custom source to SAM
	 * @param fluxFileLocation
	 * @param customSourceJarLocation
	 * @return
	 */
	public SAMComponent uploadSAMComponent(ComponentType componentType, String fluxFileLocation,
			String customSourceJarLocation) {
		
		/* Get handles to the resoures to upload */
		FileSystemResource fluxResource = createFileSystemResource(fluxFileLocation);
		FileSystemResource jarResource = createFileSystemResource(customSourceJarLocation);
		
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("componentType", componentType.name());
		
		/* Create request object */
		LinkedMultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
		requestMap.add("topologyComponentBundle", fluxResource);
		requestMap.add("bundleJar", jarResource);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);		
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>(requestMap, headers);
		
		/* Execute request */
		String url = constructRESTUrl("/catalog/streams/componentbundles/{componentType}");
		ResponseEntity<SAMComponent> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SAMComponent.class, requestParams);
		
		return response.getBody();
		
	}

	/**
	 * Return all the SAM Sources configured in SAM
	 * @return
	 */
	public Map<String, SAMComponent> getAllSAMComponents(ComponentType componentType) {
		
		Map<String, SAMComponent> allCustomSources = new HashMap<String, SAMComponent>();
		
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("componentType", componentType.name());		
		
		String url = constructRESTUrl("/catalog/streams/componentbundles/{componentType}");
		ResponseEntity<Map<String, List<SAMComponent>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SAMComponent>>>() {}, requestParams);
		List<SAMComponent> samCustomComponentList = response.getBody().get("entities");
		
		for(SAMComponent customComponent: samCustomComponentList) {
			allCustomSources.put(customComponent.getName(), customComponent);
		}
		return allCustomSources;
	
	}
	
	/**
	 * Returns a custom source for a given custom source's name
	 * @param customSourceName
	 * @return
	 */
	public SAMComponent getSAMComponent(ComponentType componentType, String customSourceName) {
		return getAllSAMComponents(componentType).get(customSourceName);
	}
	
	/**
	 * 
	 * @param customSourceName
	 */
	public void deleteCustomComponent(ComponentType componentType, String customSourceName) {
		SAMComponent samComponent = getAllSAMComponents(componentType).get(customSourceName);
		
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("sourceId", samComponent.getId().toString());
		
		String url = constructRESTUrl("/catalog/streams/componentbundles/SOURCE/{sourceId}");
		
		restTemplate.exchange(url, HttpMethod.DELETE, null, SAMComponent.class, mapParams);
		
	}	


}

package hortonworks.hdf.sam.sdk.component;

import java.util.ArrayList;
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

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.component.model.SAMComponent;
import hortonworks.hdf.sam.sdk.component.model.SAMComponentField;
import hortonworks.hdf.sam.sdk.component.model.SAMProcessorComponent;

/**
 * Java Rest Client for SAM Processors
 * @author gvetticaden
 *
 */
public class SAMProcessorComponentSDKUtils extends BaseSDKUtils {

	public SAMProcessorComponentSDKUtils(String restUrl) {
		super(restUrl);
	}

	/**
	 * Uploads a custom processor
	 * @param fluxFileLocation
	 * @param customSourceJarLocation
	 * @return
	 */
	public SAMProcessorComponent uploadCustomProcessor(String fluxFileLocation, String customSourceJarLocation) {
		/* Get handles to the resoures to upload */
		FileSystemResource fluxResource = createFileSystemResource(fluxFileLocation);
		FileSystemResource jarResource = createFileSystemResource(customSourceJarLocation);

		/* Create request object */
		LinkedMultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
		requestMap.add("customProcessorInfo", fluxResource);
		requestMap.add("jarFile", jarResource);	
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);		
		
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>(requestMap, headers);
		
		/* Execute request */
		String url = constructRESTUrl("/catalog/streams/componentbundles/PROCESSOR/custom");
		ResponseEntity<SAMComponent> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SAMComponent.class);
		
		return getCustomSAMProcessor(response.getBody().getName());
		
	}

	/**
	 * Gets a list of all custom processors in SAM
	 * @return
	 */
	public List<SAMProcessorComponent> getAllCustomSAMProcessors() {
		String url = constructRESTUrl("/catalog/streams/componentbundles/PROCESSOR");
		ResponseEntity<Map<String, List<SAMProcessorComponent>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SAMProcessorComponent>>>() {});
		List<SAMProcessorComponent> allSAMProcessorComponents = response.getBody().get("entities");
		
		List<SAMProcessorComponent> allCustomSAMProcessorComponents = new ArrayList<SAMProcessorComponent>();
		for(SAMProcessorComponent samProcessorComponent: allSAMProcessorComponents) {
			if(samProcessorComponent.getSubType().equals("CUSTOM")) {
				allCustomSAMProcessorComponents.add(samProcessorComponent);
				samProcessorComponent.setCustomProcessorName(getCustomProcessorName(samProcessorComponent.getTopologyComponentUISpecification().getFields()));
			}
		}
		return allCustomSAMProcessorComponents;
	
	}

	/**
	 * Queries the details of a custom processor searched via the processor name
	 * @param customProcessorName
	 * @return
	 */
	public SAMProcessorComponent getCustomSAMProcessor(
			String customProcessorName) {
		SAMProcessorComponent processorComponent = null;
		for (SAMProcessorComponent customProcessor: getAllCustomSAMProcessors()) {
			if(customProcessor.getCustomProcessorName().equals(customProcessorName)) {
				processorComponent = customProcessor;
				break;
			}
		}
		return processorComponent;
	}

	/**
	 * Deletes a custom processor
	 * @param customProcessorName
	 */
	public void deleteCustomSAMProcessor(String customProcessorName) {
		SAMProcessorComponent processorToDelete = getCustomSAMProcessor(customProcessorName);
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("processorId", processorToDelete.getId().toString());
		
		String url = constructRESTUrl("/catalog/streams/componentbundles/PROCESSOR/{processorId}");
		
		restTemplate.exchange(url, HttpMethod.DELETE, null, SAMProcessorComponent.class, mapParams);
		
		
	}	

	private String getCustomProcessorName(List<SAMComponentField> fields) {
		String customProcessorName = null;
		for(SAMComponentField samComponentField: fields) {
			if(samComponentField.getFieldName().equals("name")) {
				return samComponentField.getDefaultValue();
			}
		}
		return customProcessorName;
	}

	

}

package hortonworks.hdf.sam.sdk.modelregistry;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.component.model.SAMProcessorComponent;
import hortonworks.hdf.sam.sdk.modelregistry.model.PMMLModel;

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

public class SAMModelRegistrySDKUtils extends BaseSDKUtils {

	public SAMModelRegistrySDKUtils(String restUrl) {
		super(restUrl);
		// TODO Auto-generated constructor stub
	}

	public List<PMMLModel> getAllModels() {
		String url = constructRESTUrl("/catalog/ml/models");
		ResponseEntity<Map<String, List<PMMLModel>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<PMMLModel>>>() {});
		return response.getBody().get("entities");

	}

	public PMMLModel getModel(String modelName) {
		PMMLModel model = null;
		for (PMMLModel samModel: getAllModels()) {
			if(samModel.getName().equals(modelName)) {
				model = samModel;
				break;
			}
		}
		return model;
	}

	public PMMLModel addModel(String violationPredictionsModelConfigFile,
			String violationPredictionModelFile) {
		FileSystemResource pmmlModelResource = createFileSystemResource(violationPredictionModelFile);
		String pmmlModelConfigString = getStringContentOfFile(violationPredictionsModelConfigFile);
		
		/* Create request object */
		HttpHeaders modelConfigHeader = new HttpHeaders();
		modelConfigHeader.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> pmmlModelConfigHttpEntity = new HttpEntity<String>(pmmlModelConfigString, modelConfigHeader);
		
		HttpHeaders pmmlModelHeader = new HttpHeaders();
		pmmlModelHeader.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<FileSystemResource> pmmlModelHttpEntity = new HttpEntity<FileSystemResource>(pmmlModelResource, pmmlModelHeader);

			
		
		LinkedMultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
		requestMap.add("modelInfo", pmmlModelConfigHttpEntity);
		requestMap.add("pmmlFile", pmmlModelHttpEntity);			
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>(requestMap, headers);
		
		/* Execute request */
		String url = constructRESTUrl("/catalog/ml/models");
		ResponseEntity<PMMLModel> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, PMMLModel.class);
		return response.getBody();
		
		
	}

	public void deleteModel(String pmmlModelName) {
		PMMLModel modelToDelete = getModel(pmmlModelName);
		if(modelToDelete == null) {
			LOG.warn("Custom PMML Model[ " + pmmlModelName + "] doesn't exist. Nothing to delete");
			return;
		}			
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("modelId", modelToDelete.getId().toString());
		
		String url = constructRESTUrl("/catalog/ml/models/{modelId}");
		
		restTemplate.exchange(url, HttpMethod.DELETE, null, PMMLModel.class, mapParams);

		
	}

}

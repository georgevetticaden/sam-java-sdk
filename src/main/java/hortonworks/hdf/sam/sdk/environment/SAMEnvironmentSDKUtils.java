package hortonworks.hdf.sam.sdk.environment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironment;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironmentDetails;
import hortonworks.hdf.sam.sdk.environment.model.SAMServiceEnvironmentMapping;
import hortonworks.hdf.sam.sdk.environment.model.ServiceEnvironmentMapping;
import hortonworks.hdf.sam.sdk.servicepool.SAMServicePoolSDKUtils;



public class SAMEnvironmentSDKUtils extends BaseSDKUtils {

	private static final Object DEFAULT_STREAMING_ENGINE = "STORM";
	private static final Object DEFAULT_TIME_SERIES_DB = "AMBARI_METRICS";
	private static final Object DEFAULT_LOG_SEARCH_SERVICE = "AMBARI_INFRA";
	
	private SAMServicePoolSDKUtils samServicePoolSDKUtils;

	public SAMEnvironmentSDKUtils(String restUrl) {
		super(restUrl);
		this.samServicePoolSDKUtils = new SAMServicePoolSDKUtils(restUrl);
	}

	public List<SAMEnvironmentDetails> getAllSAMEnvironments() {
		String url = constructRESTUrl("/catalog/namespaces?detail=true");
		ResponseEntity<Map<String, List<SAMEnvironmentDetails>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SAMEnvironmentDetails>>>() {});
		return response.getBody().get("entities");
	}

	public SAMEnvironmentDetails getSAMEnvironment(String samDemoEnvName) {
		SAMEnvironmentDetails envDetails = null;
		for (SAMEnvironmentDetails samEnv: getAllSAMEnvironments()) {
			if(samEnv.getNamespace().getName().equals(samDemoEnvName)) {
				envDetails = samEnv;
				break;
			}
		}
		return envDetails;
	}

	public SAMEnvironment createEnvironment(String envName, String envDescription) {
		/* Create request object */
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("name", envName);
		requestMap.put("description", envDescription);
		requestMap.put("streamingEngine", DEFAULT_STREAMING_ENGINE);
		requestMap.put("timeSeriesDB", DEFAULT_TIME_SERIES_DB);
		requestMap.put("logSearchService", DEFAULT_LOG_SEARCH_SERVICE);
		
		
		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestMap, headers);
		String url = constructRESTUrl("/catalog/namespaces");

		/* need to add this in secure SAM env or else 405 error occrs */		
		restTemplate.optionsForAllow(url);
		
		//Execute the request
		ResponseEntity<SAMEnvironment> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SAMEnvironment.class);
		
		return response.getBody();
	}

	public void deleteEnvironment(String envNameToDelete) {
		// get the env id
		SAMEnvironmentDetails samEnvToDelete = getSAMEnvironment(envNameToDelete);
		if(samEnvToDelete == null) {
			LOG.warn("Environment[" + envNameToDelete + "] doesn't exist. Nothing to delete");
			return;
		}	
		
		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("envId", samEnvToDelete.getNamespace().getId().toString());

		String url = constructRESTUrl("/catalog/namespaces/{envId}");
		
		restTemplate.exchange(url, HttpMethod.DELETE, null, SAMEnvironment.class, mapParams);	
		
	}

	public void mapServicesToEnvironment(String envName,
			List<ServiceEnvironmentMapping> serviceMappings) {
		
		Map<String, Object> mapParams = new HashMap<String, Object>();
		// get the env id
		SAMEnvironmentDetails samEnv = getSAMEnvironment(envName);
		if(samEnv == null) {
			String errMsg = "Evironment[" + envName + "] doesn't exist. Cannot map Services";
			LOG.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		Integer environmentId = samEnv.getNamespace().getId();
		mapParams.put("envId", environmentId);
		
		/* Create Request of Service mappings */
		List<SAMServiceEnvironmentMapping> mappingsRequest = new ArrayList<SAMServiceEnvironmentMapping>();
		for(ServiceEnvironmentMapping mapping: serviceMappings) {
			SAMServiceEnvironmentMapping mappingRequest = createMappingRequest(new Long(environmentId), mapping);
			mappingsRequest.add(mappingRequest);
		}
		
		/* Execute call */
		String url = constructRESTUrl("/catalog/namespaces/{envId}/mapping/bulk");
		restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(mappingsRequest), SAMEnvironment.class, mapParams);

	}

	private SAMServiceEnvironmentMapping createMappingRequest(Long environmentId, ServiceEnvironmentMapping mapping) {
		

		SAMServiceEnvironmentMapping request = new SAMServiceEnvironmentMapping();
		request.setNamespaceId(environmentId);
		request.setServiceName(mapping.getServiceName());
		
		String clusterName = mapping.getServicePoolName();
		Long servicePoolId = samServicePoolSDKUtils.getServicePool(clusterName).getCluster().getId();
		request.setClusterId(servicePoolId);
		
		return request;
	}

}

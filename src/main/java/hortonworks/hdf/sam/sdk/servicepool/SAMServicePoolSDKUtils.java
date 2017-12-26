package hortonworks.hdf.sam.sdk.servicepool;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.servicepool.model.SAMServicePool;
import hortonworks.hdf.sam.sdk.servicepool.model.SAMServicePoolDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public class SAMServicePoolSDKUtils extends BaseSDKUtils {

	public SAMServicePoolSDKUtils(String restUrl) {
		super(restUrl);
	}

	public SAMServicePool createServicePool(String clusterName, String ambariUrl) {
		
		/* Create request object */
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("name", clusterName);
		requestMap.put("ambariImportUrl", ambariUrl);
		requestMap.put("description", "Ambari Cluster");
		
		
		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestMap, headers);
		String url = constructRESTUrl("/catalog/clusters");

		ResponseEntity<SAMServicePool> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SAMServicePool.class);
		
		return response.getBody();
		
		
	}

	public SAMServicePoolDetails getServicePool(String servicePoolName) {
		SAMServicePoolDetails servicePoolToFind = null;
		for (SAMServicePoolDetails samServicePool: getAllSAMServicePools()) {
			if(samServicePool.getCluster().getName().equals(servicePoolName)) {
				servicePoolToFind = samServicePool;
				break;
			}
		}
		return servicePoolToFind;
	}
	
	public List<SAMServicePoolDetails> getAllSAMServicePools() {
		String url = constructRESTUrl("/catalog/clusters?detail=true");
		ResponseEntity<Map<String, List<SAMServicePoolDetails>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SAMServicePoolDetails>>>() {});
		return response.getBody().get("entities");
	}

	public void deleteServicePool(String servicePoolName) {
		// get the service pool id
		SAMServicePoolDetails servicePoolToDelete = getServicePool(servicePoolName);
		if(servicePoolToDelete == null) {
			LOG.warn("Service Pool[" + servicePoolName + "] doesn't exist. Nothing to delete");
			return;
		}	
		
		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("servicePoolId", servicePoolToDelete.getCluster().getId().toString());

		String url = constructRESTUrl("/catalog/clusters/{servicePoolId}");
		
		restTemplate.exchange(url, HttpMethod.DELETE, null, SAMServicePool.class, mapParams);			
		
	}

	public Map<String, Object> importAmbariServices(String servicePoolName, String username, String password) {
		
		// get the service pool id
		SAMServicePoolDetails servicePool = getServicePool(servicePoolName);
		if(servicePool == null) {
			String errMsg = "Service Pool[" + servicePoolName + "] doesn't exist. Cannot import Services";
			LOG.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		
		/* Create request object */
		Map<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("clusterId", servicePool.getCluster().getId());
		requestMap.put("username", username);
		requestMap.put("password", password);
		requestMap.put("ambariRestApiRootUrl", servicePool.getCluster().getAmbariImportUrl());
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<Map<String, Object>>(requestMap, headers);
		String url = constructRESTUrl("/catalog/cluster/import/ambari");

		ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, new ParameterizedTypeReference<Map<String, Object>>(){});
		return response.getBody();
	}	



}

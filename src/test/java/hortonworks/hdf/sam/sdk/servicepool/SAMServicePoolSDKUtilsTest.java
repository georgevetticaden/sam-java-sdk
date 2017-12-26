package hortonworks.hdf.sam.sdk.servicepool;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.servicepool.model.SAMServicePool;
import hortonworks.hdf.sam.sdk.servicepool.model.SAMServicePoolDetails;

import java.util.Map;

import org.junit.Test;

public class SAMServicePoolSDKUtilsTest extends BaseSDKUtilsTest {
	
	private static final String SERVICE_POOL_NAME_TO_CREATE = "streamanalytics";
	private static final String SERVICE_POOL_AMBRI_URL = "http://hdf-3-1-build0.field.hortonworks.com:8080/api/v1/clusters/streamanalytics";
	
	private SAMServicePoolSDKUtils samServicePoolSDK = new SAMServicePoolSDKUtils (SAM_REST_URL);
	
	
	@Test
	public void createServicePoolAndImportServices() {
		createServicePool();
		importAmbariServices();
	}
	
	
	@Test
	public void createServicePool() {
		String clusterName = SERVICE_POOL_NAME_TO_CREATE;
		String ambariUrl = SERVICE_POOL_AMBRI_URL;
		SAMServicePool servicePool = samServicePoolSDK.createServicePool(clusterName, ambariUrl);
		assertNotNull(servicePool);
		assertNotNull(servicePool.getId());
		LOG.info(servicePool.toString());
	}
	
	@Test
	public void getServicePool() {
		String  servicePoolName = SERVICE_POOL_NAME_TO_CREATE;
		SAMServicePoolDetails servicePool = samServicePoolSDK.getServicePool(servicePoolName);
		assertNotNull(servicePool);
		LOG.info(servicePool.toString());
	}
	
	@Test
	public void deleteServicePool() {
		String  servicePoolName = SERVICE_POOL_NAME_TO_CREATE;
		
		//validate the test doesn't exist
		SAMServicePoolDetails servicePool = samServicePoolSDK.getServicePool(servicePoolName);
		assertNotNull(servicePool);
		
		//delete Test case
		samServicePoolSDK.deleteServicePool(servicePoolName);

		
		//validate the test doesn't exist
		servicePool = samServicePoolSDK.getServicePool(servicePoolName);
		assertNull(servicePool);
		
	}	
	
	@Test
	public void importAmbariServices() {
		String  servicePoolName = SERVICE_POOL_NAME_TO_CREATE;
		String userId = "admin";
		String password = "admin";
		Map<String, Object> importServicesResults = samServicePoolSDK.importAmbariServices(servicePoolName, userId, password);
		assertNotNull(importServicesResults);
		LOG.info(importServicesResults.toString());
	}

}

package hortonworks.hdf.sam.sdk.servicepool.manager;

import org.junit.Test;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.servicepool.model.SAMServicePoolDetails;
import static org.junit.Assert.assertNotNull;

public class SAMServicePoolManagerTest extends BaseSDKUtilsTest{

	public static final String HDF_SERVICE_POOL_NAME_TO_CREATE = "streamanalytics_JUNIT";
	public static final String HDF_SERVICE_POOL_AMBRI_URL = "http://hdf-3-1-build0.field.hortonworks.com:8080/api/v1/clusters/streamanalytics";	
	
	public static final String HDP_SERVICE_POOL_NAME_TO_CREATE = "datalake_JUNIT";
	public static final String HDP_LAKE_SERVICE_POOL_AMBRI_URL = "http://hdp-2-6-3-ga0.field.hortonworks.com:8080/api/v1/clusters/datalake";	
	
	private SAMServicePoolManager samServicePoolMaanager = new SAMServicePoolManagerImpl(SAM_REST_URL);
	
	@Test
	public void deleteServicePools() {
		samServicePoolMaanager.deleteServicePool(HDF_SERVICE_POOL_NAME_TO_CREATE);
		samServicePoolMaanager.deleteServicePool(HDP_SERVICE_POOL_NAME_TO_CREATE);
	}
	
	@Test
	public void createServicePools() {
		createStreamAnaltyicsServicePool();
		createDataLakeServicePool();
	}
	
	@Test
	public void createStreamAnaltyicsServicePool() {
		
		String clusterName = HDF_SERVICE_POOL_NAME_TO_CREATE;
		String ambariUrl = HDF_SERVICE_POOL_AMBRI_URL;
		SAMServicePoolDetails details = samServicePoolMaanager.createServicePool(clusterName, ambariUrl, "admin", "admin");
		assertNotNull(details);
		LOG.info(details.toString());
	}
	
	@Test
	public void createDataLakeServicePool() {
		
		String clusterName = HDP_SERVICE_POOL_NAME_TO_CREATE;
		String ambariUrl = HDP_LAKE_SERVICE_POOL_AMBRI_URL;
		SAMServicePoolDetails details = samServicePoolMaanager.createServicePool(clusterName, ambariUrl, "admin", "admin");
		assertNotNull(details);
		LOG.info(details.toString());
	}	
	

}

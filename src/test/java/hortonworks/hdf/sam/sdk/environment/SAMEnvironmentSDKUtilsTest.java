package hortonworks.hdf.sam.sdk.environment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironment;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironmentDetails;
import hortonworks.hdf.sam.sdk.environment.model.ServiceEnvironmentMapping;
import hortonworks.hdf.sam.sdk.servicepool.manager.SAMServicePoolManagerTest;

public class SAMEnvironmentSDKUtilsTest extends BaseSDKUtilsTest {
	
	private static final String SAM_DEMO_ENV_NAME = "Dev2";
	private SAMEnvironmentSDKUtils samEnvSDKUtils = new SAMEnvironmentSDKUtils(SAM_REST_URL);
	
	@Test
	public void createEnvironment() {
		String name = SAM_DEMO_ENV_NAME;
		String description = "test environment";
		SAMEnvironment env = samEnvSDKUtils.createEnvironment(name, description);
		assertNotNull(env);
		LOG.info(env.toString());
	
	}
	
	@Test
	public void mapServicesToEnvironment() {
		String envName = SAM_DEMO_ENV_NAME;
		
		List<ServiceEnvironmentMapping> serviceMappings = createServiceMappings();
		samEnvSDKUtils.mapServicesToEnvironment(envName,serviceMappings);
		
		SAMEnvironmentDetails samDemoEnv =samEnvSDKUtils.getSAMEnvironment(envName);
		LOG.info(samDemoEnv.toString());
	}	
	
	@Test
	public void deleteEnvironment() {
		String  envNameToDelete = SAM_DEMO_ENV_NAME;
		
		//validate the test doesn't exist
		SAMEnvironmentDetails samDemoEnv =samEnvSDKUtils.getSAMEnvironment(envNameToDelete);
		assertNotNull(samDemoEnv);
		
		//delete Test case
		samEnvSDKUtils.deleteEnvironment(envNameToDelete);

		
		//validate the test doesn't exist
		samDemoEnv =samEnvSDKUtils.getSAMEnvironment(envNameToDelete);
		assertNull(samDemoEnv);
	}
		
	

	


	@Test
	public void getSAMDevEnvironment() {
		SAMEnvironmentDetails samDemoEnv =samEnvSDKUtils.getSAMEnvironment(SAM_DEMO_ENV_NAME);
		assertNotNull(samDemoEnv);
		LOG.info(samDemoEnv.toString());
	}
	
	
	@Test
	public void getAllSAMEnvironments() {
		List<SAMEnvironmentDetails> allModels = samEnvSDKUtils.getAllSAMEnvironments();
		LOG.info(allModels.toString());
	}	
	
	
	private List<ServiceEnvironmentMapping> createServiceMappings() {
		List<ServiceEnvironmentMapping> mappings = new ArrayList<ServiceEnvironmentMapping>();
		
		String hdfServicePool = SAMServicePoolManagerTest.HDF_SERVICE_POOL_NAME_TO_CREATE;
		mappings.add(new ServiceEnvironmentMapping(hdfServicePool, "STORM"));
		mappings.add(new ServiceEnvironmentMapping(hdfServicePool, "KAFKA"));
		mappings.add(new ServiceEnvironmentMapping(hdfServicePool, "ZOOKEEPER"));
		mappings.add(new ServiceEnvironmentMapping(hdfServicePool, "AMBARI_INFRA"));
		mappings.add(new ServiceEnvironmentMapping(hdfServicePool, "AMBARI_METRICS"));
		
		String hdpServicePool = SAMServicePoolManagerTest.HDP_SERVICE_POOL_NAME_TO_CREATE;
		mappings.add(new ServiceEnvironmentMapping(hdpServicePool, "DRUID"));
		mappings.add(new ServiceEnvironmentMapping(hdpServicePool, "HBASE"));
		mappings.add(new ServiceEnvironmentMapping(hdpServicePool, "HDFS"));		
		return mappings;
	}	
	
		
}

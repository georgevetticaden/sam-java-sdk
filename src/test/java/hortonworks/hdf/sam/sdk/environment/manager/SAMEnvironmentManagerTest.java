package hortonworks.hdf.sam.sdk.environment.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironmentDetails;
import hortonworks.hdf.sam.sdk.environment.model.ServiceEnvironmentMapping;
import hortonworks.hdf.sam.sdk.servicepool.manager.SAMServicePoolManagerTest;

public class SAMEnvironmentManagerTest extends BaseSDKUtilsTest {
	
	private SAMEnvironmentManager samEnvManager = new SAMEnvironmentManagerImpl(SAM_REST_URL);
	private static final String SAM_DEMO_ENV_NAME = "Dev2";
	
	
	@Test
	public void createDemoEnv() {
		String name = SAM_DEMO_ENV_NAME;
		SAMEnvironmentDetails envDetails = samEnvManager.createSAMEnvironment(name, "Test Env", createServiceMappings());
		assertNotNull(envDetails);
		LOG.info(envDetails.toString());
	}
	
	@Test
	public void deleteDemoEnv() {
		String envToDeleteName = SAM_DEMO_ENV_NAME;
		samEnvManager.deleteSAMEnvironment(envToDeleteName);
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

package hortonworks.hdf.sam.sdk.environment;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironment;

public class SAMEnvironmentSDKUtilsTest extends BaseSDKUtilsTest {
	
	private static final String SAM_DEMO_ENV_NAME = "Dev2";
	private SAMEnvironmentSDKUtils samEnvSDKUtils = new SAMEnvironmentSDKUtils(SAM_REST_URL);
	
	@Test
	public void getAllSAMEnvironments() {
		List<SAMEnvironment> allModels = samEnvSDKUtils.getAllSAMEnvironments();
		LOG.info(allModels.toString());
	}	
	
	@Test
	public void getSAMDevEnvironment() {
		SAMEnvironment samDemoEnv =samEnvSDKUtils.getSAMEnvironment(SAM_DEMO_ENV_NAME);
		assertNotNull(samDemoEnv);
		LOG.info(samDemoEnv.toString());
	}		
}

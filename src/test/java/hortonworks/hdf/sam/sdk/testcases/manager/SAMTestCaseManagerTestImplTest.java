package hortonworks.hdf.sam.sdk.testcases.manager;

import java.util.Map;

import org.junit.Test;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.testcases.model.SamComponent;
import static org.junit.Assert.assertNotNull;

public class SAMTestCaseManagerTestImplTest extends BaseSDKUtilsTest {
	
	SAMTestCaseManager testCaseManager = new SAMTestCaseManagerImpl(BaseSDKUtilsTest.SAM_REST_URL);

	@Test
	public void executeSAMTestCase() throws Exception{
		String appName = "streaming-ref-app";
		String testName = "Test-Normal-Event";
		Integer testTimeOutInSeconds = 300;
		Map<String, Object> result = testCaseManager.runTestCaseReturnGenericMapOfResults(appName, testName, testTimeOutInSeconds);
		LOG.info(result.toString());
	}
	
	@Test
	public void executeTestNormalEventTestCaseforStreamingRefApp() throws Exception{
		String appName = "streaming-ref-app";
		String testName = "Test-Normal-Event";
		Integer testTimeOutInSeconds = 300;
		Map<String, SamComponent> result = testCaseManager.runTestCaseReturn(appName, testName, testTimeOutInSeconds);

	}	

}

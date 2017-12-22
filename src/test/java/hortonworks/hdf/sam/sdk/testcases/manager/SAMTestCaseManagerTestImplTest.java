package hortonworks.hdf.sam.sdk.testcases.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.core.io.Resource;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.testcases.model.SamTestComponent;
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
		Map<String, List<SamTestComponent>> result = testCaseManager.runTestCase(appName, testName, testTimeOutInSeconds);
		LOG.info(result.toString());
	}	
	
	@Test
	public void createTestCase() {
		String appName = "streaming-ref-app";
		String testName = "Test-Normal-Event";		
		
		//Create map of test data for each source in the app
		Map<String, Resource> testDataForSources = new HashMap<String, Resource>();
		Resource geoStreamTestData = createClassPathResource("test-cases-source-data/normal-event-test/geo-stream-test-data.json");	
		testDataForSources.put("GeoStream", geoStreamTestData);
		
		Resource speedStreamTestData = createClassPathResource("test-cases-source-data/normal-event-test/speed-stream-test-data.json");	
		testDataForSources.put("SpeedStream", speedStreamTestData);
		
		testCaseManager.createTestCase(appName, testName, testDataForSources);
	}

	
	@Test
	public void deleteTestCase() {
		String appName = "streaming-ref-app";
		String testName = "Test-Normal-Event";
		
		testCaseManager.deleteTestCase(appName, testName);
	}
}

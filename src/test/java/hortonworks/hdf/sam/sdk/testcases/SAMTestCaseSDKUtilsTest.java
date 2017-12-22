package hortonworks.hdf.sam.sdk.testcases;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.testcases.SAMTestCaseSDKUtils;
import hortonworks.hdf.sam.sdk.testcases.model.SAMTestCase;
import hortonworks.hdf.sam.sdk.testcases.model.SamTestComponent;
import hortonworks.hdf.sam.sdk.testcases.model.TestCaseExecution;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Test;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SAMTestCaseSDKUtilsTest extends BaseSDKUtilsTest {
	

	private static final String SAM_APP_NAME = "streaming-ref-app";
	private SAMTestCaseSDKUtils samTestCaseUtils = new SAMTestCaseSDKUtils(SAM_REST_URL);
	
	
	@Test
	public void createNormalEventTestCase() {
		String appName = SAM_APP_NAME;
		String testName = "Test-Normal-Event";
		
		//validate the test doesn't exist
		Map<String, Object> testCaseMap = samTestCaseUtils.getTestCase(appName, testName);
		assertNull(testCaseMap);
		
		//create test case

		SAMTestCase testCase = samTestCaseUtils.createTestCase(appName, testName );
		assertNotNull(testCase);
		LOG.info(testCase.toString());
		
		//validate the test exists
		testCaseMap = samTestCaseUtils.getTestCase(appName, testName);
		assertNotNull(testCaseMap);
		LOG.info(testCaseMap.toString());
		
	}
	
	@Test
	public void addTestDataToTestCase() {
		String appName = SAM_APP_NAME;
		String testName = "Test-Normal-Event";		
		
		//Create map of test data for each source in the app
		Map<String, Resource> testDataForSources = new HashMap<String, Resource>();
		Resource geoStreamTestData = createClassPathResource("test-cases-source-data/normal-event-test/geo-stream-test-data.json");	
		testDataForSources.put("GeoStream", geoStreamTestData);
		
		Resource speedStreamTestData = createClassPathResource("test-cases-source-data/normal-event-test/speed-stream-test-data.json");	
		testDataForSources.put("SpeedStream", speedStreamTestData);
		
		samTestCaseUtils.addTestDataToTestCase(appName, testName, testDataForSources);
	}

	
	@Test
	public void deleteNormalEventTestCaset() {
		String appName = SAM_APP_NAME;
		String testName = "Test-Normal-Event";
		
		//validate the test doesn't exist
		Map<String, Object> testCaseMap = samTestCaseUtils.getTestCase(appName, testName);
		assertNotNull(testCaseMap);
		
		//delete Test case
		samTestCaseUtils.deleteTestCase(appName, testName);

		
		//validate the test doesn't exist
		testCaseMap = samTestCaseUtils.getTestCase(appName, testName);
		assertNull(testCaseMap);
		
	}
		
	
	@Test
	public void getTestCaseExecutionResultsForByAppNameAndTestCaseExecutionIdReturnSamComponents() {
		String appName = SAM_APP_NAME;
		Integer testCaseExecutionId = 15;
		
		Map<String, List<SamTestComponent>> testCaseExecutionResults = samTestCaseUtils.getTestCaseExecutionResults(appName, testCaseExecutionId);
		assertNotNull(testCaseExecutionResults);
		LOG.info(testCaseExecutionResults.toString());
		
		/* Validate the fields from the two streams were joined */
		assertThat(testCaseExecutionResults.get("JOIN").size(), is (1));
		SamTestComponent joinComponentResult = testCaseExecutionResults.get("JOIN").get(0);
		assertNotNull(joinComponentResult);	
		Map<String, String> joinFieldAndValues = joinComponentResult.getFieldsAndValues();
		
		String speedString = joinFieldAndValues.get("speed");
		assertNotNull(speedString);
		Long speedLong = Long.valueOf(speedString);
		assertThat(speedLong, is(58L));
		
		String latitudeString = joinFieldAndValues.get("latitude");
		assertNotNull(latitudeString);
		Double latDouble = Double.valueOf(latitudeString);
		assertThat(latDouble,  is(40.7));
		
		/* Validate the that the filter worked in that no  events were considered violaiton events */
		assertNull( testCaseExecutionResults.get("Filter"));
		
	}	
	
	@Test
	public void runTestCase() throws Exception {
		String appName = SAM_APP_NAME;
		String testName = "Test-Normal-Event";
		Integer testTimeOutInSeconds = 200;
		
		TestCaseExecution testExecuteResponse = samTestCaseUtils.runTestCase(appName, testName, testTimeOutInSeconds);
		LOG.info(testExecuteResponse.toString());
	}
	

	
	@Test
	public void getTestCaseExecutionsForByAppNameAndTestCase() {
		String appName = SAM_APP_NAME;
		String testName = "Test-Normal-Event";
		
		LinkedList<TestCaseExecution> testCaseExecutions = samTestCaseUtils.getTestCaseExecutions(appName, testName);
		assertNotNull(testCaseExecutions);
		LOG.info("Number of test case exuction is: " + testCaseExecutions.size());
		LOG.info(testCaseExecutions.getLast().toString());
	}
	
	@Test
	public void getTestCaseExecutionForByAppNameAndTestCaseExecutionId() {
		String appName = SAM_APP_NAME;
		Integer testCaseExecutionId = 18;
		
		TestCaseExecution testCaseExecution = samTestCaseUtils.getTestCaseExecutions(appName, testCaseExecutionId);
		assertNotNull(testCaseExecution);
		LOG.info(testCaseExecution.toString());
	}
	
	@Test
	public void getTestCaseExecutionResultsForByAppNameAndTestCaseExecutionId() {
		String appName = SAM_APP_NAME;
		Integer testCaseExecutionId = 15;
		
		Map<String, Object> testCaseExecutionResults = samTestCaseUtils.getTestCaseExecutionResultsAsGenericMap(appName, testCaseExecutionId);
		assertNotNull(testCaseExecutionResults);
		LOG.info(testCaseExecutionResults.toString());
	}	

		
	
	@Test
	public void getTestCaseBySAMAppNameandTestName() {
		String appName = SAM_APP_NAME;
		String testName = "Test-Normal-Event";
		
		Map<String, Object> testCase = samTestCaseUtils.getTestCase(appName, testName);
		assertNotNull(testCase);
		LOG.info(testCase.toString());
	}	
	
	@Test
	public void getTestCaseIdBySAMAppNameandTestName() {
		String appName = SAM_APP_NAME;
		String testName = "Test-Normal-Event";
		
		Integer testId = samTestCaseUtils.getTestCaseId(appName, testName);
		assertNotNull(testId);
		LOG.info(testId.toString());
	}
			
	
	@Test
	public void getTestCasesForSAMAppByAppName() throws Exception {
		String appName = SAM_APP_NAME;
		Map<String, Object> testCases = samTestCaseUtils.getTestCases(appName);
		assertNotNull(testCases);
		LOG.info(testCases.toString());
		
	}	
	
	@Test
	public void getTestCasesForSAMAppByAppId() throws Exception {
		Integer appId = 1;
		Map<String, Object> testCases = samTestCaseUtils.getTestCases(appId);
		assertNotNull(testCases);
		LOG.info(testCases.toString());
		
	}
	


	

	
	

}

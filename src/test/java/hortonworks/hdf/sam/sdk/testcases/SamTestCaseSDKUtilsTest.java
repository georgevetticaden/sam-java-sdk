package hortonworks.hdf.sam.sdk.testcases;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;
import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.testcases.SAMTestCaseSDKUtils;
import hortonworks.hdf.sam.sdk.testcases.model.SamComponent;
import hortonworks.hdf.sam.sdk.testcases.model.TestCaseExecution;

import java.util.LinkedList;
import java.util.Map;

import org.junit.Test;

public class SamTestCaseSDKUtilsTest extends BaseSDKUtilsTest {
	

	private static final String SAM_APP_NAME = "streaming-ref-app";
	private SAMTestCaseSDKUtils samTestCaseUtils = new SAMTestCaseSDKUtils(SAM_REST_URL);
	
	
	@Test
	public void getTestCaseExecutionResultsForByAppNameAndTestCaseExecutionIdReturnSamComponents() {
		String appName = SAM_APP_NAME;
		Integer testCaseExecutionId = 15;
		
		Map<String, SamComponent> testCaseExecutionResults = samTestCaseUtils.getTestCaseExecutionResults(appName, testCaseExecutionId);
		assertNotNull(testCaseExecutionResults);
		LOG.info(testCaseExecutionResults.toString());
		
		/* Validate the fields from the two streams were joined */
		SamComponent joinComponentResult = testCaseExecutionResults.get("JOIN");
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

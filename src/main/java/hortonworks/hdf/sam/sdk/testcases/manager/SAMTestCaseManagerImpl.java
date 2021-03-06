package hortonworks.hdf.sam.sdk.testcases.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import hortonworks.hdf.sam.sdk.testcases.SAMTestCaseSDKUtils;
import hortonworks.hdf.sam.sdk.testcases.model.SAMTestCase;
import hortonworks.hdf.sam.sdk.testcases.model.SamTestComponent;
import hortonworks.hdf.sam.sdk.testcases.model.TestCaseExecution;

public class SAMTestCaseManagerImpl implements SAMTestCaseManager {

	private static final Logger LOG = LoggerFactory.getLogger(SAMTestCaseManagerImpl.class);
	SAMTestCaseSDKUtils testCaseSDKUils;

	public SAMTestCaseManagerImpl(String samRestUrl) {
		this.testCaseSDKUils = new SAMTestCaseSDKUtils(samRestUrl);
	}



	@Override
	public Map<String, List<SamTestComponent>> runTestCase(String appName, String testName, Integer testTimeOutInSeconds) 
			throws Exception {
		
		/* Execute test case and wait for it to complete */
		TestCaseExecution testCaseExecution = executeAndWaitForTestToComplete(
				appName, testName, testTimeOutInSeconds);

		/* Return all results */
		return testCaseSDKUils.getTestCaseExecutionResults(appName,testCaseExecution.getId());

	}


	@Override
	public Map<String, Object> runTestCaseReturnGenericMapOfResults(String appName, String testName, Integer testTimeOutInSeconds)
			throws Exception {
		
		/* Execute test case and wait for it to complete */
		TestCaseExecution testCaseExecution = executeAndWaitForTestToComplete(appName, testName, testTimeOutInSeconds);

		/* Returns ther results */
		return testCaseSDKUils.getTestCaseExecutionResultsAsGenericMap(appName,testCaseExecution.getId());

	}

	private TestCaseExecution executeAndWaitForTestToComplete(String appName,String testName, Integer testTimeOutInSeconds)
			throws InterruptedException {
		
		long startTime = System.currentTimeMillis();
		TestCaseExecution testCaseExecution = testCaseSDKUils.runTestCase(
				appName, testName, testTimeOutInSeconds);
		LOG.info("Execution of Test Case[" + testCaseExecution
				+ "] started at time[" + new Date(startTime) + "] with Timeout[" + testTimeOutInSeconds + "]");

		// Add 20 seconds to whatever the configured Test timeout is for the
		// test to finish
		Long timeOutInMS = testTimeOutInSeconds * 1000L + 20000;

		/*
		 * Have to wait until the test executed has finisihed to fetch the
		 * results
		 */
		while (!testCaseExecution.isFinished()) {

			if ( (System.currentTimeMillis() - startTime) > timeOutInMS) {
				Date timeOutTime = new Date();
				String errMsg = "At time[" + timeOutTime + "], TimeOut["
						+ testTimeOutInSeconds + "] ocurred before Test [App="
						+ appName + ", Test Name=" + testName
						+ "] could finish";
				LOG.error(errMsg);
				throw new RuntimeException("Test has time out.");
			}
			// sleep 30 seconds
			Thread.sleep(30000);

			// get status of test execution
			testCaseExecution = testCaseSDKUils.getTestCaseExecutions(appName,
					testCaseExecution.getId());
			LOG.info("At time[" + new Date()
					+ "], Checking status of test execution again["
					+ testCaseExecution);

		}

		LOG.info("At time[" + new Date() + "], Test Case[" + testName
				+ "] finished with sucess status["
				+ testCaseExecution.isSuccess());
		if (!testCaseExecution.isSuccess()) {
			String errMsg = "Test Case[" + testName + "] failed.";
			throw new RuntimeException(errMsg);
		}
		return testCaseExecution;
	}



	@Override
	public SAMTestCase createTestCase(String appName, String testName,
			Map<String, Resource> testDataForSources) {
		
		if(testCaseSDKUils.getTestCase(appName, testName) != null) {
			String errMsg ="Test Case["+testName +" for SAM App["+appName +"] already exist";
			LOG.error(errMsg);
			throw new RuntimeException(errMsg);
		}
		LOG.info("Creating Test Case["+testName +" for SAM App["+appName +"]");
		SAMTestCase testCase = testCaseSDKUils.createTestCase(appName, testName);
		LOG.info("Creating Test Case["+testName +" for SAM App["+appName +"]");
		
		LOG.info("Adding datasaets for the Test Case["+testName +" in SAM App["+appName +"]");
		testCaseSDKUils.addTestDataToTestCase(appName, testName, testDataForSources);
		LOG.info("Finished Adding datasaets for the Test Case["+testName +" in SAM App["+appName +"]");
		
		return testCase;
		
		
		
	}



	@Override
	public void deleteTestCase(String appName, String testName) {
		testCaseSDKUils.deleteTestCase(appName, testName);
		
	}

}

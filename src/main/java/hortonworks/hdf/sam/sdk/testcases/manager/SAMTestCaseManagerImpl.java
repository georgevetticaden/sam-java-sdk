package hortonworks.hdf.sam.sdk.testcases.manager;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hortonworks.hdf.sam.sdk.testcases.SAMTestCaseSDKUtils;
import hortonworks.hdf.sam.sdk.testcases.model.SamTestComponent;
import hortonworks.hdf.sam.sdk.testcases.model.TestCaseExecution;

public class SAMTestCaseManagerImpl implements SAMTestCaseManager {

	private static final Logger LOG = LoggerFactory.getLogger(SAMTestCaseManagerImpl.class);
	SAMTestCaseSDKUtils testCaseSDKUils;
	
	
	public SAMTestCaseManagerImpl(String samRestUrl) {
		this.testCaseSDKUils = new SAMTestCaseSDKUtils(samRestUrl);
	}
	
	
	
	/* (non-Javadoc)
	 * @see hortonworks.hdf.sam.sdk.testcases.manager.SAMTestCaseManager#runTestCaseReturn(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Map<String, List<SamTestComponent>> runTestCase(String appName, String testName, Integer testTimeOutInSeconds) throws Exception {
		TestCaseExecution testCaseExecution = executeAndWaitForTestToComplete(appName, testName,
				testTimeOutInSeconds);
		
		// return test results
		return testCaseSDKUils.getTestCaseExecutionResults(appName, testCaseExecution.getId());
		
	}		
	
	/* (non-Javadoc)
	 * @see hortonworks.hdf.sam.sdk.testcases.manager.SAMTestCaseManager#runTestCaseReturnGenericMapOfResults(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public Map<String, Object> runTestCaseReturnGenericMapOfResults(String appName, String testName, Integer testTimeOutInSeconds) throws Exception {
		TestCaseExecution testCaseExecution = executeAndWaitForTestToComplete(appName, testName,
				testTimeOutInSeconds);
		
		// return test results
		return testCaseSDKUils.getTestCaseExecutionResultsAsGenericMap(appName, testCaseExecution.getId());
		
	}


	private TestCaseExecution executeAndWaitForTestToComplete(String appName, String testName,
			Integer testTimeOutInSeconds) throws InterruptedException {
		long startTime = System.currentTimeMillis();		
		TestCaseExecution testCaseExecution = testCaseSDKUils.runTestCase(appName, testName, testTimeOutInSeconds);
		LOG.info("Execution of Test Case["+ testCaseExecution + "] started at time["+new Date(startTime) + "]");
		
		// Add 20 seconds to whatever the configured Test timeout is for the test to finish
		Long timeOutInMS = testTimeOutInSeconds * 1000L * 20000;
		
		/* Have to wait until the test executed has finisihed to fetch the results */
		while (!testCaseExecution.isFinished()) {
			
			if(System.currentTimeMillis() - startTime > timeOutInMS) {
				Date timeOutTime = new Date();
				String errMsg = "At time["+timeOutTime + "], TimeOut["+testTimeOutInSeconds+"] ocurred before Test [App="+appName + ", Test Name="+testName +"] could finish";
				LOG.error(errMsg);
				throw new RuntimeException("Test has timeout");
			}
			//sleep 30 seconds
			Thread.sleep(30000);
			
			//get status of test execution
			testCaseExecution = testCaseSDKUils.getTestCaseExecutions(appName,testCaseExecution.getId());
			LOG.info("At time["+new Date() + "], Checking status of test execution again["+testCaseExecution);
			
		}
		
		LOG.info("At time["+new Date() + "], Test Case["+ testName + "] finished with sucess status["+testCaseExecution.isSuccess());
		if(!testCaseExecution.isSuccess()) {
			String errMsg = "Test Case["+testName + "] failed.";
			throw new RuntimeException(errMsg);
		}
		return testCaseExecution;
	}

	
	
}

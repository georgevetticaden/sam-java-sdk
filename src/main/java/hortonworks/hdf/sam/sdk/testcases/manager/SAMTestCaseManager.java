package hortonworks.hdf.sam.sdk.testcases.manager;

import hortonworks.hdf.sam.sdk.testcases.model.SAMTestCase;
import hortonworks.hdf.sam.sdk.testcases.model.SamTestComponent;

import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;

public interface SAMTestCaseManager {

	/**
	 * Runs a SAM Test case, waits for it complete and then returns the results as map of the SAM Component object results.
	 * The return map's key is the SAM component name and the value is the values fo the SAM Component.
	 * @param appName
	 * @param testName
	 * @param testTimeOutInSeconds
	 * @return
	 * @throws Exception
	 */
	public abstract Map<String, List<SamTestComponent>> runTestCase(String appName,
			String testName, Integer testTimeOutInSeconds) throws Exception;

	/**
	 * Runs a SAM Test case, waits for it complete and then returns the entire events of the test result as JSON Map
	 * @param appName
	 * @param testName
	 */
	public abstract Map<String, Object> runTestCaseReturnGenericMapOfResults(
			String appName, String testName, Integer testTimeOutInSeconds)
			throws Exception;

	/**
	 * Create a SAM Test Case and setups the test data to mock out each source of the App
	 * @param appName
	 * @param testName
	 * @param testDataForSources
	 * @return 
	 */
	public abstract SAMTestCase createTestCase(String appName, String testName,
			Map<String, Resource> testDataForSources);
	
	
	/**
	 * Deletes a Test Case for a given SAM App
	 * @param appName
	 * @param testName
	 */
	public abstract void deleteTestCase(String appName, String testName);

}
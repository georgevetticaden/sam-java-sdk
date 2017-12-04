package hortonworks.hdf.sam.sdk.testcases.manager;

import hortonworks.hdf.sam.sdk.testcases.model.SamComponent;

import java.util.List;
import java.util.Map;

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
	public abstract Map<String, List<SamComponent>> runTestCase(String appName,
			String testName, Integer testTimeOutInSeconds) throws Exception;

	/**
	 * Runs a SAM Test case, waits for it complete and then returns the entire events of the test result as JSON Map
	 * @param appName
	 * @param testName
	 */
	public abstract Map<String, Object> runTestCaseReturnGenericMapOfResults(
			String appName, String testName, Integer testTimeOutInSeconds)
			throws Exception;

}
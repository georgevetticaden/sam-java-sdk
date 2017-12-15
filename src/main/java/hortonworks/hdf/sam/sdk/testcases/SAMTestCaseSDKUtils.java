package hortonworks.hdf.sam.sdk.testcases;


import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.app.SAMAppSDKUtils;
import hortonworks.hdf.sam.sdk.testcases.model.SamTestComponent;
import hortonworks.hdf.sam.sdk.testcases.model.TestCaseExecution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

/**
 * Java clients for  SAM's Test Case  features powered by REST
 * @author gvetticaden
 *
 */
public class SAMTestCaseSDKUtils extends BaseSDKUtils {
	

	private SAMAppSDKUtils samAppSDKUtils;

	public SAMTestCaseSDKUtils(String restUrl) {
		super(restUrl);
		this.samAppSDKUtils = new SAMAppSDKUtils(restUrl);
	}
	
	/**
	 * Gets all test Cases for a SAM App given the SAM App Id
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTestCases(Integer appId) {
		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("appId", String.valueOf(appId));
		String url = constructRESTUrl("/catalog/topologies/{appId}/testcases");
		
		Map<String, Object> testCases = executeRESTGetCall(url, mapParams);
		return testCases;
	}
	
	/**
	 * Gets all test Cases for a SAM App given the SAm app Name 
	 * @param appName
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getTestCases(String appName) {
		
		Map<String, Object> testCases = null;
		
		Integer appId = samAppSDKUtils.getSAMApp(appName).getId();
		if(appId != null) {
			testCases = getTestCases(appId);
		}
		return testCases;
	}

	/**
	 * Returns the Test Case for a given SAM App and the test name
	 * @param appName
	 * @param testName
	 * @return
	 */
	public Map<String, Object> getTestCase(String appName, String testName) {
		Map<String, Object> testCase = null;
		Map<String, Object> samAppTestCasesMap = getTestCases(appName);
		List<Map<String, Object>> samTestCasesList = (List<Map<String, Object>>) samAppTestCasesMap.get("entities");
		for(Map<String, Object> appTestCase: samTestCasesList) {
			if(testName.equals(appTestCase.get("name"))) {
				testCase = appTestCase;
				break;
			}
		}
		return testCase;
		
	}

	
	/**
	 * Runs/execute a given testCase based on the App Name and the test Name. And Returns the status of the test execution.
	 * @param appName
	 * @param testName
	 * @param testTimeOutInSeconds
	 * @return
	 */
	public TestCaseExecution runTestCase(String appName, String testName,
			Integer testTimeOutInSeconds) {
		
		/* Get App Id */
		Integer appId = samAppSDKUtils.getSAMApp(appName).getId();
		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("appId", String.valueOf(appId));
		
		
		/* Get TestId */
		Integer testCaseId = getTestCaseId(appName, testName);
		
		/* Create Execution request */
		TestCaseExecution testCaseRequest = new TestCaseExecution(testCaseId, testTimeOutInSeconds);
		
		/* Execute test */
		String url = constructRESTUrl("/catalog/topologies/{appId}/actions/testrun");
		ResponseEntity<TestCaseExecution> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(testCaseRequest), TestCaseExecution.class, mapParams);
		return response.getBody();
	}		
	
	/**
	 * Returns the test case id given the app Name and testName
	 * @param appName
	 * @param testName
	 * @return
	 */
	public Integer getTestCaseId(String appName, String testName) {
		Map<String, Object> testCase = getTestCase(appName, testName);
		return (Integer) testCase.get("id");
	}

	/**
	 * Returns a summary history of the test executions for a given test case ordered by test run date in ascending order
	 * @param appName
	 * @param testName
	 * @return
	 */
	public LinkedList<TestCaseExecution> getTestCaseExecutions(
			String appName, String testName) {
		
		Map<Integer, TestCaseExecution> testCaseExecutionsMap = new HashMap<Integer, TestCaseExecution>();
		
		/* Get App Id */
		Integer appId = samAppSDKUtils.getSAMApp(appName).getId();
		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("appId", String.valueOf(appId));
		
		/* Get TestId */
		Integer testCaseId = getTestCaseId(appName, testName);
		
		String url = constructRESTUrl("/catalog/topologies/{appId}/testhistories");
		
		ResponseEntity<Map<String, List<TestCaseExecution>>> appTestCasesHistoryResponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<TestCaseExecution>>>() {},  mapParams);
		
		List<TestCaseExecution> appTestCasesList = appTestCasesHistoryResponse.getBody().get("entities");
		
		if(!appTestCasesList.isEmpty()) {
			for(TestCaseExecution testCaseExecution: appTestCasesList) {
				if(testCaseExecution.getTestCaseId() == testCaseId) {
					testCaseExecutionsMap.put(testCaseExecution.getId(), testCaseExecution);
				}
			}
		}
		
		return new LinkedList<TestCaseExecution>(testCaseExecutionsMap.values());
	}


	/**
	 * Returns the summary 
	 * @param appName
	 * @param testCaseExcutionId
	 * @return
	 */
	public TestCaseExecution getTestCaseExecutions(String appName,
			Integer testCaseExcutionId) {
		/* Get App Id */
		Integer appId = samAppSDKUtils.getSAMApp(appName).getId();
		Map<String, Integer> mapParams = new HashMap<>();
		mapParams.put("appId", appId);
		mapParams.put("testCaseExecutionId", testCaseExcutionId);
		
		String url = constructRESTUrl("/catalog/topologies/{appId}/testhistories/{testCaseExecutionId}");
		ResponseEntity<TestCaseExecution> response = restTemplate.exchange(url, HttpMethod.GET, null, TestCaseExecution.class, mapParams);
		
		return response.getBody();
	}

	/**
	 * Returns result of a given SAM TEst Case Execution. This is equivalement to the full test log file result after running a SAM Test Case
	 * Returns the results as a map of SAM Components where the key is the name of the SAM Component and the value is the results
	 * of the SAM Component
	 * @param appName
	 * @param testCaseExecutionId
	 * @return
	 */
	public Map<String, List<SamTestComponent>> getTestCaseExecutionResults(String appName,
			Integer testCaseExecutionId) {
		/* Get App Id */
		Integer appId = samAppSDKUtils.getSAMApp(appName).getId();
		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("appId", String.valueOf(appId));
		mapParams.put("testCaseExecutionId", String.valueOf(testCaseExecutionId));
		
		String url = constructRESTUrl("/catalog/topologies/{appId}/testhistories/{testCaseExecutionId}/events");
		ResponseEntity<Map<String, List<SamTestComponent>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SamTestComponent>>>(){}, mapParams);
		
		List<SamTestComponent> samComponentsList = response.getBody().get("entities");
		Map<String, List<SamTestComponent>> samComponentsMaps = new HashMap<String, List<SamTestComponent>>();
		for(SamTestComponent samComponent : samComponentsList) {
			String componentName = samComponent.getComponentName();
			List<SamTestComponent> samComponents = samComponentsMaps.get(componentName);
			if(samComponents == null)  {
				samComponents = new ArrayList<SamTestComponent>();
				samComponentsMaps.put(componentName,samComponents );
			}
			samComponents.add(samComponent);
		}
		return samComponentsMaps;		
		
	}	
	
	/**
	 * Returns result of a given SAM TEst Case Execution. This is equivalement to the full test log file result after running a SAM Test Case.
	 * Returns the results as Map of the JSON result
	 * @param appName
	 * @param testCaseExecutionId
	 * @return
	 */
	public Map<String, Object> getTestCaseExecutionResultsAsGenericMap(String appName,
			Integer testCaseExecutionId) {
		/* Get App Id */
		Integer appId = samAppSDKUtils.getSAMApp(appName).getId();
		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("appId", String.valueOf(appId));
		mapParams.put("testCaseExecutionId", String.valueOf(testCaseExecutionId));
		
		String url = constructRESTUrl("/catalog/topologies/{appId}/testhistories/{testCaseExecutionId}/events");
		return executeRESTGetCall(url, mapParams);
		
		
	}
	

	

	
}

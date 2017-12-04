package hortonworks.hdf.sam.sdk.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;

public class SAMAppSDKUtils extends BaseSDKUtils {

	public SAMAppSDKUtils(String restUrl) {
		super(restUrl);
	}
	
	/**
	 * Gets the details of a SAM App given the id
	 * @param appId
	 * @return
	 */
	public Map<String, Object> getSAMAppDetails(Integer appId) {

		Map<String, String> mapParams = new HashMap<>();
		mapParams.put("appId", String.valueOf(appId));
		String url = constructRESTUrl(TOPOLOGY_ACTION + "/{appId}");		
		
		Map<String, Object> appDetail = executeRESTGetCall(url, mapParams);
		return appDetail;		
	}
	
	/**
	 * Returns all the SAM Apps
	 * @return
	 */
	public Map<String, Object> getAllSAMApps() {
		String url = constructRESTUrl(TOPOLOGY_ACTION);
		
		Map<String, Object> samApps = executeRESTGetCall(url, null);
		return samApps;
	}

	/**
	 * Returns the details of a SAM App given its app name
	 * @param appName
	 * @return
	 */
	public Map<String, Object> getSAMAppDetails(String appName) {
		Map<String, Object> samAppMap = getAllSAMApps();
		List<Map<String, Object>> samAppsList =  (List<Map<String, Object>>) samAppMap.get("entities");
		for(Map<String, Object> samApp: samAppsList) {
			if(appName.equals(samApp.get("name"))) {
				return samApp;
			}
		}
		return null;
	}
	
	/**
	 * REturns the id of a SAM App given the SAM app name
	 * @param appName
	 * @return
	 */
	public Integer getSAMAppId(String appName) {
		Map<String, Object> samAppDetails = getSAMAppDetails(appName);
		Integer appId = null;
		if(samAppDetails != null) {
			appId = (Integer)samAppDetails.get("id");
		}
		return appId;
	}
	
	

}

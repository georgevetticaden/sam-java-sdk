package hortonworks.hdf.sam.sdk.app;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.app.SAMAppSDKUtils;

public class SAMAppSDKUtilsTest extends BaseSDKUtilsTest {
	
	private SAMAppSDKUtils samAppSDKUtils = new SAMAppSDKUtils(BaseSDKUtilsTest.SAM_REST_URL);
	
	@Test
	public void getSAMAppDetails() {
		Integer appId = 1;
		Map<String, Object> appDetails = samAppSDKUtils.getSAMAppDetails(appId);
		assertNotNull(appDetails);

		LOG.info(appDetails.toString());
	}
	
	@Test
	public void getAllSAMApps() {
		Map<String, Object> appDetails = samAppSDKUtils.getAllSAMApps();
		LOG.info(appDetails.toString());
	}	
	
	@Test 
	public void getSAMAppDetailsByName() {
		String appName = "streaming-ref-app";
		
		Map<String, Object> appDetails = samAppSDKUtils.getSAMAppDetails(appName);
		assertNotNull(appDetails);
		LOG.info(appDetails.toString());
		
		
	}
	
	@Test 
	public void getSAMAppId() {
		String appName = "streaming-ref-app";
		
		Integer samAppId = samAppSDKUtils.getSAMAppId(appName);
		LOG.info(samAppId.toString());
		
		
	}	

}

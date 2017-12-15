package hortonworks.hdf.sam.sdk.app;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.app.model.SAMApplication;
import hortonworks.hdf.sam.sdk.app.model.SAMApplicationStatus;

import java.util.List;

import org.junit.Test;

public class SAMAppSDKUtilsTest extends BaseSDKUtilsTest {
	
	private SAMAppSDKUtils samAppSDKUtils = new SAMAppSDKUtils(BaseSDKUtilsTest.SAM_REST_URL);
	
	private static final String SAM_ENV_NAME = "Dev";
	private static final String TRUCKING_REF_APP_ADVANCED = "streaming-ref-app-advanced" + SAM_CUSTOM_ARTIFACT_SUFFIX;
	
	@Test
	public void killTruckingRefApp() {
		SAMApplication samApp = samAppSDKUtils.killSAMApp(TRUCKING_REF_APP_ADVANCED);
		LOG.info(samApp.toString());		
	}
	
	@Test
	public void getTruckingRefAppStatus() {
		SAMApplicationStatus status = samAppSDKUtils.getSAMAppStatus(TRUCKING_REF_APP_ADVANCED);
		assertNotNull(status);
		LOG.info(status.toString());
	}
	
	@Test
	public void deployTruckingRefApp() {
		SAMApplication samApp = samAppSDKUtils.deploySAMApp(TRUCKING_REF_APP_ADVANCED);
		LOG.info(samApp.toString());
	}
	
	@Test
	public void importTruckingRefAppAdvanced() {
		String samImportFile = SAM_EXTENSIONS_HOME + "/sam-app/streaming-ref-app-advanced.json";
		SAMApplication samApp = samAppSDKUtils.importSAMApp(TRUCKING_REF_APP_ADVANCED, SAM_ENV_NAME, samImportFile);
		assertNotNull(samApp);
		LOG.info(samApp.toString());
	}
	
	@Test 
	public void getSAMTruckingRefApp() {		
		SAMApplication appDetails = samAppSDKUtils.getSAMApp(TRUCKING_REF_APP_ADVANCED);
		assertNotNull(appDetails);
		LOG.info(appDetails.toString());
	}

	@Test
	public void deleteTruckingRefAppAdvanced() {
		SAMApplication appToDelete = samAppSDKUtils.getSAMApp(TRUCKING_REF_APP_ADVANCED);
		assertNotNull(appToDelete);
		
		samAppSDKUtils.deleteSAMApp(TRUCKING_REF_APP_ADVANCED);
		
		appToDelete = samAppSDKUtils.getSAMApp(TRUCKING_REF_APP_ADVANCED);
		assertNull(appToDelete);		
	}	
	
	@Test
	public void getAllSAMApps() {
		List<SAMApplication> appDetails = samAppSDKUtils.getAllSAMApps();
		LOG.info(appDetails.toString());
	}	
	

	
	

}

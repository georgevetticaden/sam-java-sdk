package hortonworks.hdf.sam.sdk.app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.app.model.SAMAppSource;
import hortonworks.hdf.sam.sdk.app.model.SAMApplication;
import hortonworks.hdf.sam.sdk.app.model.SAMApplicationStatus;

import java.util.List;

import org.junit.Test;
import org.springframework.core.io.Resource;

public class SAMAppSDKUtilsTest extends BaseSDKUtilsTest {
	
	private SAMAppSDKUtils samAppSDKUtils = new SAMAppSDKUtils(BaseSDKUtilsTest.SAM_REST_URL);
	
	private static final String SAM_ENV_NAME = "Dev";
	private static final String TRUCKING_REF_APP_ADVANCED = "streaming-ref-app-advanced" + SAM_CUSTOM_ARTIFACT_SUFFIX;
	
	@Test
	public void setUpTruckingRefApp() {
		importTruckingRefAppAdvanced();
		deployTruckingRefApp();
	}
	
	
	@Test
	public void tearDownTruckingRefApp() {
		killTruckingRefApp();
		deleteTruckingRefAppAdvanced();
	}
	
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
		Resource samImportResource = createFileSystemResource(samImportFile);
		SAMApplication samApp = samAppSDKUtils.importSAMApp(TRUCKING_REF_APP_ADVANCED, SAM_ENV_NAME, samImportResource);
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
		String appName = TRUCKING_REF_APP_ADVANCED;
		//String appName =  "test";
		SAMApplication appToDelete = samAppSDKUtils.getSAMApp(appName);
		assertNotNull(appToDelete);
		
		samAppSDKUtils.deleteSAMApp(appName);
		
		appToDelete = samAppSDKUtils.getSAMApp(appName);
		assertNull(appToDelete);		
	}	
	
	@Test
	public void getAllSAMApps() {
		List<SAMApplication> appDetails = samAppSDKUtils.getAllSAMApps();
		LOG.info(appDetails.toString());
	}	
	

	@Test
	public void getSAMAppSources() {
		String appName = "streaming-ref-app";
		List<SAMAppSource> appSources = samAppSDKUtils.getSAMAppSources(appName);
		assertNotNull(appSources);
		assertThat(appSources.size(), is(2));
		LOG.info(appSources.toString());
	}

	@Test
	public void getSAMAppSource() {
		String appName = "streaming-ref-app";
		String sourceName = "GeoStream";
		SAMAppSource appSource = samAppSDKUtils.getSAMAppSource(appName, sourceName);
		assertNotNull(appSource);
		LOG.info(appSource.toString());
	}	
	

}

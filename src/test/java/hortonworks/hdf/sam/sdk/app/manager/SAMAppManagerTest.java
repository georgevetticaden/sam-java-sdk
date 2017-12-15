package hortonworks.hdf.sam.sdk.app.manager;

import static org.junit.Assert.assertNotNull;
import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.app.model.SAMApplicationStatus;

import org.junit.Test;
import org.springframework.core.io.Resource;

public class SAMAppManagerTest extends BaseSDKUtilsTest {

	private SAMAppManager samAppManager = new SAMAppManagerImpl(SAM_REST_URL);
	
	private static final String TRUCKING_REF_APP_ADVANCED = "streaming-ref-app-advanced" + SAM_CUSTOM_ARTIFACT_SUFFIX;	
	private static final String SAM_ENV_NAME = "Dev";
	
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
	public void importTruckingRefAppAdvanced() {
		String samImportFile = SAM_EXTENSIONS_HOME + "/sam-app/streaming-ref-app-advanced.json";
		Resource samImportResource = createFileSystemResource(samImportFile);
		samAppManager.importSAMApplication(TRUCKING_REF_APP_ADVANCED, SAM_ENV_NAME, samImportResource);
	}
	
	public void deployTruckingRefApp() {
		SAMApplicationStatus appStatus = samAppManager.deploySAMApplication(TRUCKING_REF_APP_ADVANCED, 35);
		assertNotNull(appStatus);
		LOG.info(appStatus.toString());
	}
	
	@Test
	public void killTruckingRefApp() {
		samAppManager.killSAMApplication(TRUCKING_REF_APP_ADVANCED, 35);
	}
	
	@Test
	public void deleteTruckingRefAppAdvanced() {
		samAppManager.deleteSAMApplication(TRUCKING_REF_APP_ADVANCED);
	}
	
	
}

package hortonworks.hdf.sam.sdk.modelregistry;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.modelregistry.model.PMMLModel;

import java.util.List;

import org.junit.Test;

public class SAMModelRegistrySDKUtilsTest extends BaseSDKUtilsTest {

	private static final String VIOLATION_PREDICTION_MODEL_NAME = "DriverViolationPredictionModel" + SAM_CUSTOM_ARTIFACT_SUFFIX;
	private SAMModelRegistrySDKUtils modelRegistrySDKUtils = new SAMModelRegistrySDKUtils(SAM_REST_URL);
	

	@Test
	public void addViolationPredictionModelToModelRegistry() {
		String violationPredictionModelFile = SAM_EXTENSIONS_HOME + "/custom-pmml-model/DriverViolationLogisticalRegessionPredictionModel-pmml.xml";
		String violationPredictionsModelConfigFile = SAM_EXTENSIONS_HOME + "/custom-pmml-model/config/DriverViolationLogisticalRegessionPredictionModel-config.json";
		PMMLModel violatonModel = modelRegistrySDKUtils.addModel(violationPredictionsModelConfigFile, violationPredictionModelFile);
		assertNotNull(violatonModel);;
		LOG.debug("Violation PMML Model created: " + violatonModel);
	}
	
	@Test
	public void getViolationPredictionModel() {
		PMMLModel violationModel = modelRegistrySDKUtils.getModel(VIOLATION_PREDICTION_MODEL_NAME);
		assertNotNull(violationModel);
		LOG.info(violationModel.toString());
	}	
	
	@Test
	public void deleteViolationPredictionModel() {
		PMMLModel violationModel = modelRegistrySDKUtils.getModel(VIOLATION_PREDICTION_MODEL_NAME);
		assertNotNull(violationModel);
		
		modelRegistrySDKUtils.deleteModel(VIOLATION_PREDICTION_MODEL_NAME);
		
		violationModel = modelRegistrySDKUtils.getModel(VIOLATION_PREDICTION_MODEL_NAME);
		assertNull(violationModel);		
	}	
	
	@Test
	public void getAllModels() {
		List<PMMLModel> allModels = modelRegistrySDKUtils.getAllModels();
		LOG.info(allModels.toString());
	}	
	

}

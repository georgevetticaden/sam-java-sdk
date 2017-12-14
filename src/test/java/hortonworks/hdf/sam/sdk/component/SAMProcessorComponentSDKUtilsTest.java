package hortonworks.hdf.sam.sdk.component;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.component.model.SAMComponent;
import hortonworks.hdf.sam.sdk.component.model.SAMProcessorComponent;

public class SAMProcessorComponentSDKUtilsTest extends BaseSDKUtilsTest {
	
	private static final String ENRICH_WEATER_PROCCESSOR_NAME = "ENRICH-WEATHER"+SAM_CUSTOM_ARTIFACT_SUFFIX;
	private static final String NORMALIZE_MODEL_PROCESSOR_NAME = "NORMALIZE-MODEL-FEATURES"+SAM_CUSTOM_ARTIFACT_SUFFIX;
	private static final String PHOENIX_ENRICHEMNT_PROCESSOR_NAME = "ENRICH-PHOENIX"+SAM_CUSTOM_ARTIFACT_SUFFIX;
	private SAMProcessorComponentSDKUtils processorSDKUtils  = new SAMProcessorComponentSDKUtils(SAM_REST_URL);

	
	
	@Test
	public void uploadAllCustomProcessorsForRefApp() {
		uploadWeatherEnrichmentSAMProcessor();
		uploadNormalizeModelSAMProcessor();
		uploadPhoenixEnrichmentSAMProcessor();
	}
	
	@Test
	public void deleteAllCustomProcessorsRefApp() {
		deleteWeatherEnrichmentSAMProcessor();
		deleteNormalizeModelSAMProcessor();
		deletePhoenixEnrichmentSAMProcessor();
	}
	
	@Test
	public void uploadWeatherEnrichmentSAMProcessor() {
		String fluxFile = SAM_EXTENSIONS_HOME + "/custom-processor/config/weather-enrichment-processor-component.json";
		String customProcessorJar = SAM_EXTENSIONS_HOME + "/custom-processor/sam-custom-processor-"+ SAM_EXTENSIONS_VERSION +".jar";
		SAMProcessorComponent samComponent = processorSDKUtils.uploadCustomProcessor(fluxFile, customProcessorJar);
		LOG.info("The Weather Enrichment Processor created is: " + samComponent.toString());
	}
	
	@Test
	public void getWeatherEnrichmentSAMProcessor() {
		SAMProcessorComponent customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(ENRICH_WEATER_PROCCESSOR_NAME);
		assertNotNull(customSAMProcessor);
		LOG.info(customSAMProcessor.toString());
	}	
	
	@Test
	public void deleteWeatherEnrichmentSAMProcessor() {
		SAMProcessorComponent customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(ENRICH_WEATER_PROCCESSOR_NAME);
		assertNotNull(customSAMProcessor);
		
		processorSDKUtils.deleteCustomSAMProcessor(ENRICH_WEATER_PROCCESSOR_NAME);
		
		customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(ENRICH_WEATER_PROCCESSOR_NAME);
		assertNull(customSAMProcessor);
		
	}
	
	@Test
	public void uploadNormalizeModelSAMProcessor() {
		String fluxFile = SAM_EXTENSIONS_HOME + "/custom-processor/config/normalize-model-features-processor-component.json";
		String customProcessorJar = SAM_EXTENSIONS_HOME + "/custom-processor/sam-custom-processor-" + SAM_EXTENSIONS_VERSION +"a.jar";
		SAMProcessorComponent samComponent = processorSDKUtils.uploadCustomProcessor(fluxFile, customProcessorJar);
		LOG.info("The Normalize Model Processor created is: " + samComponent.toString());
	}
	
	@Test
	public void getNormalizeModelSAMProcessor() {
		SAMProcessorComponent customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(NORMALIZE_MODEL_PROCESSOR_NAME);
		assertNotNull(customSAMProcessor);
		LOG.info(customSAMProcessor.toString());
	}	
	
	@Test
	public void deleteNormalizeModelSAMProcessor() {
		SAMProcessorComponent customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(NORMALIZE_MODEL_PROCESSOR_NAME);
		assertNotNull(customSAMProcessor);
		
		processorSDKUtils.deleteCustomSAMProcessor(NORMALIZE_MODEL_PROCESSOR_NAME);
		
		customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(NORMALIZE_MODEL_PROCESSOR_NAME);
		assertNull(customSAMProcessor);
		
	}	
	
	@Test
	public void uploadPhoenixEnrichmentSAMProcessor() {
		String fluxFile = SAM_EXTENSIONS_HOME + "/custom-processor/config/phoenix-enrichment-processor-component.json";
		String customProcessorJar = SAM_EXTENSIONS_HOME + "/custom-processor/sam-custom-processor-"+ SAM_EXTENSIONS_VERSION +"-jar-with-dependencies.jar";
		SAMProcessorComponent samComponent = processorSDKUtils.uploadCustomProcessor(fluxFile, customProcessorJar);
		LOG.info("The Phoenix EnrichmentProcessor created is: " + samComponent.toString());
	}
	
	@Test
	public void getPhoenixEnrichmentSAMProcessor() {
		SAMProcessorComponent customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(PHOENIX_ENRICHEMNT_PROCESSOR_NAME);
		assertNotNull(customSAMProcessor);
		LOG.info(customSAMProcessor.toString());
	}	
	
	@Test
	public void deletePhoenixEnrichmentSAMProcessor() {
		SAMProcessorComponent customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(PHOENIX_ENRICHEMNT_PROCESSOR_NAME);
		assertNotNull(customSAMProcessor);
		
		processorSDKUtils.deleteCustomSAMProcessor(PHOENIX_ENRICHEMNT_PROCESSOR_NAME);
		
		customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(PHOENIX_ENRICHEMNT_PROCESSOR_NAME);
		assertNull(customSAMProcessor);
		
	}		
	
//	@Test
//	public void getAllCustomSAMProcessors() {
//		List<SAMProcessorComponent> customSAMProcessors = processorSDKUtils.getAllCustomSAMProcessors();
//		LOG.info(customSAMProcessors.toString());
//	}
	


}

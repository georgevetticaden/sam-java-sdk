package hortonworks.hdf.sam.sdk.component;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.component.model.SAMComponent;
import hortonworks.hdf.sam.sdk.component.model.SAMProcessorComponent;

public class SAMProcessorComponentSDKUtilsTest extends BaseSDKUtilsTest {
	
	private SAMProcessorComponentSDKUtils processorSDKUtils  = new SAMProcessorComponentSDKUtils(SAM_REST_URL);

	
	@Test
	public void uploadCustomProcessor() {
		String fluxFile = "/Users/gvetticaden/Dropbox/Hortonworks/Development/Git/sam-custom-extensions/sam-custom-processor/src/main/resources/flux/weather-enrichment-processor-component.json";
		String customProcessorJar = "/Users/gvetticaden/Dropbox/Hortonworks/Development/Git/sam-custom-extensions/sam-custom-processor/target/sam-custom-processor-0.0.11.jar";
		SAMProcessorComponent samComponent = processorSDKUtils.uploadCustomProcessor(fluxFile, customProcessorJar);
		LOG.info(samComponent.toString());
	}
	
	@Test
	public void getAllCustomSAMProcessors() {
		List<SAMProcessorComponent> customSAMProcessors = processorSDKUtils.getAllCustomSAMProcessors();
		LOG.info(customSAMProcessors.toString());
	}
	
	@Test
	public void getCustomSAMProcessor() {
		String customProcessorName = "ENRICH-WEATHER-NEW";
		SAMProcessorComponent customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(customProcessorName);
		assertNotNull(customSAMProcessor);
		LOG.info(customSAMProcessor.toString());
	}	
	
	@Test
	public void deleteCustomSAMProcessor() {
		String customProcessorName = "ENRICH-WEATHER-NEW";
		SAMProcessorComponent customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(customProcessorName);
		assertNotNull(customSAMProcessor);
		
		processorSDKUtils.deleteCustomSAMProcessor(customProcessorName);
		
		customSAMProcessor = processorSDKUtils.getCustomSAMProcessor(customProcessorName);
		assertNull(customSAMProcessor);
		
	}

}

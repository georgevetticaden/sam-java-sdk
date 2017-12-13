package hortonworks.hdf.sam.sdk.component;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.component.model.ComponentType;
import hortonworks.hdf.sam.sdk.component.model.SAMComponent;

import org.junit.Test;

public class SAMSourceSinkComponentSDKUtilsTest extends BaseSDKUtilsTest {
	
	private static final String KINESIS_SAM_SOURCE_NAME = "Kinesis-NEW";
	private static final String S3_SAM_SINK_NAME = "S3-NEW";
	private SAMSourceSinkComponentSDKUtils samProcesserSDK = new SAMSourceSinkComponentSDKUtils(SAM_REST_URL);

	
	@Test
	public void uploadCustomKinesisSource() {
		String fluxFileLocation = "/Users/gvetticaden/Dropbox/Hortonworks/HDP-Emerging-Products/A-HDF/A-Master-Demo/all-custom-extensions/3.1/3.1.0.0-270/Sam-Custom-Extensions/aws-integration/kinesis/kinesis-source-topology-component.json";
		String customSourceJarLocation = "/Users/gvetticaden/Dropbox/Hortonworks/HDP-Emerging-Products/A-HDF/A-Master-Demo/all-custom-extensions/3.1/3.1.0.0-270/Sam-Custom-Extensions/aws-integration/kinesis/sam-custom-source-kinesis-0.0.10.jar";
		SAMComponent samCustomSource = samProcesserSDK.uploadSAMComponent(ComponentType.SOURCE, fluxFileLocation, customSourceJarLocation);
		assertNotNull(samCustomSource);
		LOG.info(samCustomSource.toString());
	}
	
	
//	@Test
//	public void getAllSources() {
//		Map<String, SAMComponent> allSourcecs = samProcesserSDK.getAllSAMComponents(ComponentType.SOURCE);
//		LOG.info(allSourcecs.toString());
//	}	
//	
	@Test
	public void getCustomKinesisSource() {
		String customSourceName = KINESIS_SAM_SOURCE_NAME;
		SAMComponent samCustomSource = samProcesserSDK.getSAMComponent(ComponentType.SOURCE, customSourceName);
		assertNotNull(samCustomSource);
		LOG.info(samCustomSource.toString());
	}
	
	@Test
	public void deleteCustomKinesisSource() {
		String customSourceName = KINESIS_SAM_SOURCE_NAME;
		SAMComponent samCustomSource = samProcesserSDK.getSAMComponent(ComponentType.SOURCE, customSourceName);
		assertNotNull(samCustomSource);
		samProcesserSDK.deleteCustomComponent(ComponentType.SOURCE,  customSourceName);
		samCustomSource = samProcesserSDK.getSAMComponent(ComponentType.SOURCE, customSourceName);
		assertNull(samCustomSource);
	}
	
	@Test
	public void uploadCustomS3Sink() {
		String fluxFileLocation = "/Users/gvetticaden/Dropbox/Hortonworks/HDP-Emerging-Products/A-HDF/A-Master-Demo/all-custom-extensions/3.1/3.1.0.0-270/Sam-Custom-Extensions/aws-integration/s3/s3-sink-topology-component.json";
		String customSinkJarLocation = "/Users/gvetticaden/Dropbox/Hortonworks/HDP-Emerging-Products/A-HDF/A-Master-Demo/all-custom-extensions/3.1/3.1.0.0-270/Sam-Custom-Extensions/aws-integration/s3/sam-custom-sink-s3-0.0.10.jar";		
		SAMComponent samCustomSink = samProcesserSDK.uploadSAMComponent(ComponentType.SINK, fluxFileLocation, customSinkJarLocation);
		assertNotNull(samCustomSink);
		LOG.info(samCustomSink.toString());
		
	}	
	
	@Test
	public void getCustomS3Sink() {
		String customSinkName = S3_SAM_SINK_NAME;
		SAMComponent samCustomSink = samProcesserSDK.getSAMComponent(ComponentType.SINK, customSinkName);
		assertNotNull(samCustomSink);
		LOG.info(samCustomSink.toString());
	}	
	
	@Test
	public void deleteCustomS3Sink() {
		String customSinkName = S3_SAM_SINK_NAME;
		SAMComponent samCustomSink = samProcesserSDK.getSAMComponent(ComponentType.SINK, customSinkName);
		assertNotNull(samCustomSink);
		samProcesserSDK.deleteCustomComponent(ComponentType.SINK,  customSinkName);
		samCustomSink = samProcesserSDK.getSAMComponent(ComponentType.SINK, customSinkName);
		assertNull(samCustomSink);
	}	
	

}

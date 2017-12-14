package hortonworks.hdf.sam.sdk.udf;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import hortonworks.hdf.sam.sdk.BaseSDKUtilsTest;
import hortonworks.hdf.sam.sdk.component.model.SAMProcessorComponent;
import hortonworks.hdf.sam.sdk.udf.model.SAMUDF;

import java.util.List;

import org.junit.Test;

public class SAMUDFSDKUtilsTest extends BaseSDKUtilsTest {
	
	
	private static final String ROUND_UDF_NAME = "ROUND"+SAM_CUSTOM_ARTIFACT_SUFFIX;
	private static final String TIMESTAMP_LONG_UDF_NAME = "TIMESTAMP_LONG"+SAM_CUSTOM_ARTIFACT_SUFFIX;
	private static final String GET_WEEK_UDF_NAME = "GET_WEEK"+SAM_CUSTOM_ARTIFACT_SUFFIX;
	
	private SAMUDFSDKUtils udfSDKUtils = new SAMUDFSDKUtils(SAM_REST_URL);
	
	@Test
	public void uploadAllCustomUDFsForRefApp() {
		uploadRoundUDF();
		uploadTimestampLongUDF();
		uploadGetWeekUDF();
	}

	@Test
	public void deleteAllCustomUDFsForRefApp() {
		deleteRoundUDF();
		deleteTimestampLongUDF();
		deleteGetWeekUDF();
	}	
	
	
	@Test
	public void uploadRoundUDF() {
		String udfConfigFile = SAM_EXTENSIONS_HOME
				+ "/custom-udf/config/round-udf-config.json";
		String udfJar = SAM_EXTENSIONS_HOME
				+ "/custom-udf/sam-custom-udf-"+ SAM_EXTENSIONS_VERSION +".jar";
		SAMUDF roundUdf = udfSDKUtils.uploadUDF(udfConfigFile, udfJar);
		LOG.info("The Round UDF created is: " + roundUdf.toString());
	}
	
	@Test
	public void getRoundUDF() { 
		SAMUDF roundUDF = udfSDKUtils.getUDF(ROUND_UDF_NAME);
		assertNotNull(roundUDF);
		LOG.info(roundUDF.toString());
	}		
	
	@Test
	public void deleteRoundUDF() {
		SAMUDF roundUDF = udfSDKUtils.getUDF(ROUND_UDF_NAME);
		assertNotNull(roundUDF);
		
		udfSDKUtils.deleteUDF(ROUND_UDF_NAME);
		
		roundUDF = udfSDKUtils.getUDF(ROUND_UDF_NAME);
		assertNull(roundUDF);
		
	}	
	
	@Test
	public void uploadTimestampLongUDF()  {
		String udfConfigFile = SAM_EXTENSIONS_HOME
				+ "/custom-udf/config/timestamp-long-udf.json";
		String udfJar = SAM_EXTENSIONS_HOME
				+ "/custom-udf/sam-custom-udf-"+ SAM_EXTENSIONS_VERSION +".jar";
		SAMUDF roundUdf = udfSDKUtils.uploadUDF(udfConfigFile, udfJar);
		LOG.info("The TimestmapLong UDF created is: " + roundUdf.toString());
	}
	
	@Test
	public void getTimeStampLongUDF() {
		SAMUDF timeStampLongUdf = udfSDKUtils.getUDF(TIMESTAMP_LONG_UDF_NAME);
		assertNotNull(timeStampLongUdf);
		LOG.info(timeStampLongUdf.toString());
	}	
	
	
	@Test
	public void deleteTimestampLongUDF() {
		SAMUDF udfToDelete = udfSDKUtils.getUDF(TIMESTAMP_LONG_UDF_NAME);
		assertNotNull(udfToDelete);
		
		udfSDKUtils.deleteUDF(TIMESTAMP_LONG_UDF_NAME);
		
		udfToDelete = udfSDKUtils.getUDF(TIMESTAMP_LONG_UDF_NAME);
		assertNull(udfToDelete);
		
	}	
	
	@Test
	public void uploadGetWeekUDF()  {
		String udfConfigFile = SAM_EXTENSIONS_HOME
				+ "/custom-udf/config/get-week-udf.json";
		String udfJar = SAM_EXTENSIONS_HOME
				+ "/custom-udf/sam-custom-udf-"+ SAM_EXTENSIONS_VERSION +".jar";
		SAMUDF udfAdded = udfSDKUtils.uploadUDF(udfConfigFile, udfJar);
		LOG.info("The GetWeek UDF created is: " + udfAdded.toString());
	}
	
	@Test
	public void getWeekUDF() {
		SAMUDF getWeekUDF = udfSDKUtils.getUDF(GET_WEEK_UDF_NAME);
		assertNotNull(getWeekUDF);
		LOG.info(getWeekUDF.toString());
	}	
	
	@Test
	public void deleteGetWeekUDF() {
		SAMUDF udfToDelete = udfSDKUtils.getUDF(GET_WEEK_UDF_NAME);
		assertNotNull(udfToDelete);
		
		udfSDKUtils.deleteUDF(GET_WEEK_UDF_NAME);
		
		udfToDelete = udfSDKUtils.getUDF(GET_WEEK_UDF_NAME);
		assertNull(udfToDelete);
		
	}		
	
	@Test
	public void getAllSAMUDFs() {
		List<SAMUDF> udfs = udfSDKUtils.getAllUDFs();
		LOG.info(udfs.toString());
	}	
	
	
	
	
	
	
	


}

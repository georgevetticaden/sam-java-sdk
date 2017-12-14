package hortonworks.hdf.sam.sdk.udf;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.component.model.SAMProcessorComponent;
import hortonworks.hdf.sam.sdk.udf.model.SAMUDF;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;

public class SAMUDFSDKUtils extends BaseSDKUtils {

	public SAMUDFSDKUtils(String restUrl) {
		super(restUrl);
	}

	public List<SAMUDF> getAllUDFs() {
		String url = constructRESTUrl("/catalog/streams/udfs");
		ResponseEntity<Map<String, List<SAMUDF>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SAMUDF>>>() {});
		return response.getBody().get("entities");
	}

	public SAMUDF getUDF(String roundUdfName) {
		SAMUDF udf = null;
		for (SAMUDF samUdf: getAllUDFs()) {
			if(samUdf.getName().equals(roundUdfName)) {
				udf = samUdf;
				break;
			}
		}
		return udf;
	}

	public SAMUDF uploadUDF(String udfConfigFile, String udfJar) {
		/* Get handles to the resoures to upload */
		String udfConfigString = getStringContentOfFile(udfConfigFile);
		FileSystemResource udfJarResource = createFileSystemResource(udfJar);
		
		HttpHeaders udfConfigHeader = new HttpHeaders();
		udfConfigHeader.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> udfConfigHttpEntity = new HttpEntity<String>(udfConfigString, udfConfigHeader);
		
		HttpHeaders udfJarHeader = new HttpHeaders();
		udfJarHeader.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<FileSystemResource> udfJarHttpEntity = new HttpEntity<FileSystemResource>(udfJarResource, udfJarHeader);
		
		/* Create request object */
		LinkedMultiValueMap<String, Object> requestMap = new LinkedMultiValueMap<String, Object>();
		requestMap.add("udfConfig", udfConfigHttpEntity);
		requestMap.add("udfJarFile", udfJarHttpEntity);		
		
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String,Object>>(requestMap, headers);
		
		/* Execute request */
		String url = constructRESTUrl("/catalog/streams/udfs");
		ResponseEntity<SAMUDF> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, SAMUDF.class);
			
		return response.getBody();
		
	}

	public void deleteUDF(String roundUdfName) {
		SAMUDF udfToDelete = getUDF(roundUdfName);
		Map<String, String> mapParams = new HashMap<String, String>();
		mapParams.put("udfId", udfToDelete.getId().toString());
		
		String url = constructRESTUrl("/catalog/streams/udfs/{udfId}");
		
		restTemplate.exchange(url, HttpMethod.DELETE, null, SAMUDF.class, mapParams);
		
	}
	
	

}

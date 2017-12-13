package hortonworks.hdf.sam.sdk;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class BaseSDKUtils {
	
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());	
	protected static final String TOPOLOGY_ACTION = "/catalog/topologies";
	
	private String samRESTUrl;
	protected ObjectMapper mapper;
	protected RestTemplate restTemplate;
	

	public BaseSDKUtils(String restUrl) {
		
		if(StringUtils.isEmpty(restUrl)) {
			throw new RuntimeException("SAMRestUril cannot not be empty");
		}
		this.samRESTUrl = restUrl;
		this.restTemplate = new RestTemplate();
		this.mapper = new ObjectMapper();

	}
	
	protected Map<String, Object> executeRESTGetCall(String url, Map<String, String> params) {		
		
		try {
			String responseBody = getResponseBody(url, params);
			return mapper.readValue(responseBody, Map.class);			
		} catch (Exception e) {
			String errMsg = "Error executing Rest Call["+ url + "] with params["+params+"]";
			LOG.error(errMsg, e);
			throw new RuntimeException(errMsg, e);
		}

	}
	
	protected String getResponseBody(String url, Map<String, String> params) {
		ResponseEntity<String> response = null;
		if(params != null)
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(constructAuthHeader()), String.class, params);
		else
			response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(constructAuthHeader()), String.class);	
		String responseBody = response.getBody();
		return responseBody;
	}
	

	
	protected String constructRESTUrl(String actionUrl) {
		return samRESTUrl + actionUrl;
	}	
	
	protected HttpHeaders constructAuthHeader() {
		String plainCreds = "adminj:adminj";
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);	
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		
		return headers;
	}	
	
	protected FileSystemResource createFileSystemResource(String filePath) {
		File file = null;
		try {
			file = ResourceUtils.getFile(ResourceUtils.FILE_URL_PREFIX + filePath);
		} catch (Exception e) {
			String errMsg = "Error loading file["+filePath + "]";
			LOG.error(errMsg);
			throw new RuntimeException(errMsg, e);
		}
		if(!file.exists()) {
			String errMsg = "File["+filePath + "] cannot be found";
			LOG.error(errMsg);
			throw new RuntimeException(errMsg);
		}	
		return new FileSystemResource(file);
	}	

}

package hortonworks.hdf.sam.sdk;


import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.ResourceUtils;

public class BaseSDKUtilsTest {
	
	protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	protected static final String SAM_REST_URL = "http://hdf-3-1-build3.field.hortonworks.com:7777/api/v1";	
	protected static final String SAM_EXTENSIONS_HOME = "/Users/gvetticaden/Dropbox/Hortonworks/HDP-Emerging-Products/A-HDF/A-Master-Demo/all-custom-extensions/3.1/3.1.0.0-270/Sam-Custom-Extensions";
	protected static final String SAM_EXTENSIONS_VERSION = "0.0.11";
	protected static final String SAM_CUSTOM_ARTIFACT_SUFFIX="_AUTOCREATED";
	
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

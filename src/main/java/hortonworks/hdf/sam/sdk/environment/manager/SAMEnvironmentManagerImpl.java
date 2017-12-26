package hortonworks.hdf.sam.sdk.environment.manager;

import hortonworks.hdf.sam.sdk.environment.SAMEnvironmentSDKUtils;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironmentDetails;
import hortonworks.hdf.sam.sdk.environment.model.ServiceEnvironmentMapping;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMEnvironmentManagerImpl implements SAMEnvironmentManager {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(SAMEnvironmentManagerImpl.class);
	private SAMEnvironmentSDKUtils samEnvironmentSDKUtils;
	
	
	public SAMEnvironmentManagerImpl(String samRestUrl) {
		super();
		this.samEnvironmentSDKUtils = new SAMEnvironmentSDKUtils(samRestUrl);
	}


	/* (non-Javadoc)
	 * @see hortonworks.hdf.sam.sdk.environment.manager.SAMEnvironmentManager#createSAMEnvironment(java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public SAMEnvironmentDetails createSAMEnvironment(String envName, String description, List<ServiceEnvironmentMapping> services) {
		
		LOG.info("Creating SAM Environment["+ envName + "]");		
		samEnvironmentSDKUtils.createEnvironment(envName, description);
		
		LOG.info("Starting mappings services[["+ services + "] to Environment["+ envName + "]");
		samEnvironmentSDKUtils.mapServicesToEnvironment(envName, services);
		LOG.info("Finished mappings services[["+ services + "] to Environment["+ envName + "]");
		
		return samEnvironmentSDKUtils.getSAMEnvironment(envName);
		
	}


	@Override
	public void deleteSAMEnvironment(String envName) {
		
		LOG.info("Deleting SAM Environment["+ envName + "]");	
		samEnvironmentSDKUtils.deleteEnvironment(envName);
		
	}

}

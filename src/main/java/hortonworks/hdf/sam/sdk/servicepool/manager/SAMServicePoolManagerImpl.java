package hortonworks.hdf.sam.sdk.servicepool.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hortonworks.hdf.sam.sdk.servicepool.SAMServicePoolSDKUtils;
import hortonworks.hdf.sam.sdk.servicepool.model.SAMServicePoolDetails;

public class SAMServicePoolManagerImpl implements SAMServicePoolManager {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(SAMServicePoolManagerImpl.class);
	
	private SAMServicePoolSDKUtils samServicePoolSDKUtils;

	public SAMServicePoolManagerImpl(String samRestUrl) {
		this.samServicePoolSDKUtils = new SAMServicePoolSDKUtils(samRestUrl);
	}
	
	@Override
	public SAMServicePoolDetails createServicePool(String clusterName, String ambariUrl, String username, String password) {
		
		LOG.info("Creating Service Pool from Ambari Cluster["+ clusterName + "] using Ambari Rest Endpoint["+ ambariUrl + "]");
		samServicePoolSDKUtils.createServicePool(clusterName, ambariUrl);
		
		LOG.info("Starting importing services into Service Pool from Ambari Cluster["+ clusterName + "] using Ambari Rest Endpoint["+ ambariUrl + "]");
		samServicePoolSDKUtils.importAmbariServices(clusterName, username, password);
		LOG.info("Finished importing services into Service Pool from Ambari Cluster["+ clusterName + "] using Ambari Rest Endpoint["+ ambariUrl + "]");

		return samServicePoolSDKUtils.getServicePool(clusterName);
	}

	@Override
	public void deleteServicePool(String clusterName) {
		LOG.info("Deleting Service Pool[" + clusterName + "]");
		samServicePoolSDKUtils.deleteServicePool(clusterName);
		
	}

}

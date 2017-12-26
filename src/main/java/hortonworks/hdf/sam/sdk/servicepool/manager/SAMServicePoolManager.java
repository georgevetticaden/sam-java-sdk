package hortonworks.hdf.sam.sdk.servicepool.manager;

import hortonworks.hdf.sam.sdk.servicepool.model.SAMServicePoolDetails;

public interface SAMServicePoolManager {

	/**
	 * Creates a Service Pool and imports the Ambari Services into the Service Pool based on the ambariUrl
	 * @param clusterName
	 * @param ambariUrl
	 * @param username
	 * @param password
	 * @return
	 */
	public abstract SAMServicePoolDetails createServicePool(String clusterName,
			String ambariUrl, String username, String password);

	/**
	 * Deletes the Service Pool from SAM
	 * @param clusterName
	 */
	public abstract void deleteServicePool(String clusterName);

}
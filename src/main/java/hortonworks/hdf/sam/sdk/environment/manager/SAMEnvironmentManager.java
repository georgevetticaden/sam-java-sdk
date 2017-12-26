package hortonworks.hdf.sam.sdk.environment.manager;

import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironmentDetails;
import hortonworks.hdf.sam.sdk.environment.model.ServiceEnvironmentMapping;

import java.util.List;

public interface SAMEnvironmentManager {

	/**
	 * Creates SAM Enviornment and maps services from Service Pools that environment
	 * @param envName
	 * @param description
	 * @param services
	 * @return
	 */
	public abstract SAMEnvironmentDetails createSAMEnvironment(String envName,
			String description, List<ServiceEnvironmentMapping> services);
	
	
	/**
	 * Deletes the specified SAM Environment
	 * @param envName
	 */
	public abstract void deleteSAMEnvironment(String envName);

}
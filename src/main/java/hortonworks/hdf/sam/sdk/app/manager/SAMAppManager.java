package hortonworks.hdf.sam.sdk.app.manager;

import hortonworks.hdf.sam.sdk.app.model.SAMApplication;
import hortonworks.hdf.sam.sdk.app.model.SAMApplicationStatus;

import org.springframework.core.io.Resource;

public interface SAMAppManager {

	/**
	 * Deploys a SAM Application waiting to complete deployment or timeout based on timeOutPerids.
	 * If the application is not sucessfully deployed for giventimeout, it will throw exception
	 * @param appName
	 * @param deployTimeoutSeconds
	 * @return
	 */
	public abstract SAMApplicationStatus deploySAMApplication(String appName,
			int deployTimeoutSeconds);

	
	/**
	 * Imports the SAM Application
	 * @param appName
	 * @param samEnvName
	 * @param samAppImportFile
	 * @return
	 */
	public abstract SAMApplication importSAMApplication(String appName, String samEnvName,
			Resource samAppImportFile);


	/**
	 * Kills/Undeploys the SAM application. If the kill exceeds killTimeOut then exception ges thrown
	 * @param appName
	 * @param killTimeOutSeconds
	 */
	public abstract void killSAMApplication(String appName, int killTimeOutSeconds);


	/**
	 * Kills/Undeploys the SAM application with default timeout
	 * @param samAppName
	 */
	public abstract void killSAMApplication(String samAppName);
	
	/**
	 * Deletes the SAM application from SAM
	 * @param appName
	 */
	public abstract void deleteSAMApplication(String appName);



}
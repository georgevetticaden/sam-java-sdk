package hortonworks.hdf.sam.sdk.app.manager;

import hortonworks.hdf.sam.sdk.app.SAMAppSDKUtils;
import hortonworks.hdf.sam.sdk.app.model.SAMApplication;
import hortonworks.hdf.sam.sdk.app.model.SAMApplicationStatus;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

public class SAMAppManagerImpl implements SAMAppManager {
	
	
	private static final Logger LOG = LoggerFactory.getLogger(SAMAppManagerImpl.class);

	private static final int DEFAULT_KILL_TIMEOUT_SECONDS = 35;;
	
	private SAMAppSDKUtils samAppSDKUtils;
	
	public SAMAppManagerImpl (String samRestUrl) {
		this.samAppSDKUtils = new SAMAppSDKUtils(samRestUrl);
	}
	
	/* (non-Javadoc)
	 * @see hortonworks.hdf.sam.sdk.app.manager.SAMAppManager#deploySAMApplication(java.lang.String, java.lang.Long)
	 */
	@Override
	public SAMApplicationStatus deploySAMApplication(String appName, int deployTimeoutSeconds)    {
		
		/* First check if app is deployed and if so, kill it before deploying */
		if(samAppSDKUtils.isAppDeployed(appName)) {
			LOG.info("SAM App[" + appName + "] is already deployed. Going to kill it first");
			killSAMApplication(appName, DEFAULT_KILL_TIMEOUT_SECONDS);
		}
			
		DateTime startTime = new DateTime();
		LOG.info("Starting deployment of SAM App[" + appName+ "] started at time[" + startTime.toString() + "]");		
				
		/* Deploy the app */
		samAppSDKUtils.deploySAMApp(appName);
		
		/* Wait for 1 seconds */
		pause(1000);
		
		Seconds deployTime = Seconds.secondsBetween(startTime, new DateTime());
		
		SAMApplicationStatus appStatus = null;
		/* Keep checking status of deployed App until deployTimeout is passed */
		while(deployTime.getSeconds() < deployTimeoutSeconds) {
			
			
			try {
				LOG.info("Checking status of deployment for SAM App[" + appName + ']');
				appStatus =  samAppSDKUtils.getSAMAppStatus(appName);
				/* if appStatus is return then deployment completed and could be successfull or failed so break */
				break;	
			} catch ( Exception e) {
				//exception is thrown when app is still not deployed. so pause again and then check status
				pause(1000);
				deployTime = Seconds.secondsBetween(startTime, new DateTime());
			}			
		}
		
		DateTime finishTime = new DateTime();
		int deploymentTime = Seconds.secondsBetween(startTime, finishTime).getSeconds();
		if(appStatus != null) {
			if("ACTIVE".equals(appStatus.getStatus())) {
				LOG.info("SAM App [" + appName + "] successfully deployed. Deployement Time [" + deploymentTime + " seconds]");
				return appStatus;
			} else {
				String errMsg = "SAM App[" + appName + "] fiinish deployment but wasn't successful: " + appStatus.toString();
				throw new RuntimeException(errMsg);
			}
		} else {
			/* if app status is still null. Then deploymentment didn't finish in time duration */
			String errMsg = "SAM App[" + appName + "] didn't finish in configured timeout[" + deployTimeoutSeconds + "]";
			throw new RuntimeException(errMsg);
		}
		
		
	}
	
	
	@Override
	public SAMApplication importSAMApplication(String appName, String samEnvName, Resource samAppImportFile) {
		return samAppSDKUtils.importSAMApp(appName, samEnvName, samAppImportFile);
	}
	
	
	@Override
	public void killSAMApplication(String samAppName) {
		killSAMApplication(samAppName, DEFAULT_KILL_TIMEOUT_SECONDS);
		
	}
	
	@Override
	public void killSAMApplication(String appName, int killTimeOutSeconds) {
		
		DateTime startTime = new DateTime();
		LOG.info("Starting killing of SAM App[" + appName+ "] started at time[" + startTime.toString() + "]");		
		
		/* Kill SAM App */
		samAppSDKUtils.killSAMApp(appName);
		
		/* Wait for 1 seconds */
		pause(1000);
		
		Seconds killTime = Seconds.secondsBetween(startTime, new DateTime());
		
		while(killTime.getSeconds() < killTimeOutSeconds) {
			try {
				LOG.info("Checking status of killing the SAM App[" + appName + ']');
				
				SAMApplicationStatus appStatus =  samAppSDKUtils.getSAMAppStatus(appName);
				
				/* If status is returned. Then app is still alive. Pause and check status again */
				pause(1000);
				killTime = Seconds.secondsBetween(startTime, new DateTime());
				
			} catch ( Exception e) {
				DateTime finishTime = new DateTime();
				int killedTime = Seconds.secondsBetween(startTime, finishTime).getSeconds();
				String msg = "SAM App[" + appName + "] successfully killed. Kill Time[" + killedTime + " second]";
				LOG.info(msg);
				return;
			}				
		}
		
		String errMsg = "SAM App[" + appName + "] did not get killed within killTimeout[" + killTimeOutSeconds +" seconds]";
		throw new RuntimeException(errMsg);
	}
	
	
	@Override
	public void deleteSAMApplication(String appName) {
		samAppSDKUtils.deleteSAMApp(appName);
	}

	private void pause(long waitPeriodinMs) {
		try {
			Thread.sleep(waitPeriodinMs);
		} catch (InterruptedException e1) {
			
		}
	}



}

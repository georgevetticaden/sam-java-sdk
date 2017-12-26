package hortonworks.hdf.sam.sdk.environment.model;

import java.io.Serializable;

public class ServiceEnvironmentMapping implements Serializable {
	


	private static final long serialVersionUID = 6849717118683285582L;
	private String servicePoolName;
	private String serviceName;
	
	public ServiceEnvironmentMapping(String servicePoolName, String serviceName) {
		super();
		this.servicePoolName = servicePoolName;
		this.serviceName = serviceName;
	}	
	
	public String getServicePoolName() {
		return servicePoolName;
	}
	public void setServicePoolName(String servicePoolName) {
		this.servicePoolName = servicePoolName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	

}

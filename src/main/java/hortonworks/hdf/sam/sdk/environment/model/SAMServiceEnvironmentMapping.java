package hortonworks.hdf.sam.sdk.environment.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)	
public class SAMServiceEnvironmentMapping implements Serializable {


	private static final long serialVersionUID = -6293710813787721049L;
	
	private long clusterId;
	private long namespaceId;
	private String serviceName;
	public long getClusterId() {
		return clusterId;
	}
	public void setClusterId(long clusterId) {
		this.clusterId = clusterId;
	}
	public long getNamespaceId() {
		return namespaceId;
	}
	public void setNamespaceId(long namespaceId) {
		this.namespaceId = namespaceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	
		

}

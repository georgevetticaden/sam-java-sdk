package hortonworks.hdf.sam.sdk.servicepool.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)	
public class SAMServicePoolDetails implements Serializable {

	private static final long serialVersionUID = 8026463618295512350L;
	
	private SAMServicePool cluster;
	private List<SAMServiceDetails> services;
	
	public SAMServicePool getCluster() {
		return cluster;
	}
	public void setCluster(SAMServicePool cluster) {
		this.cluster = cluster;
	}

	public List<SAMServiceDetails> getServices() {
		return services;
	}
	public void setServices(List<SAMServiceDetails> services) {
		this.services = services;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	
	

}

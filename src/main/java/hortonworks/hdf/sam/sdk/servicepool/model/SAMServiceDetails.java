package hortonworks.hdf.sam.sdk.servicepool.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)	
public class SAMServiceDetails implements Serializable {
	

	private static final long serialVersionUID = -6295021122095943376L;
	private SAMService service;

	public SAMService getService() {
		return service;
	}

	public void setService(SAMService service) {
		this.service = service;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}		
}

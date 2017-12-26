package hortonworks.hdf.sam.sdk.servicepool.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)	
public class SAMServicePool implements Serializable {

	private static final long serialVersionUID = 8122920780240986197L;
	
	private Long id;
	private String name;
	private String ambariImportUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAmbariImportUrl() {
		return ambariImportUrl;
	}
	public void setAmbariImportUrl(String ambariImportUrl) {
		this.ambariImportUrl = ambariImportUrl;
	}
	

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	
}

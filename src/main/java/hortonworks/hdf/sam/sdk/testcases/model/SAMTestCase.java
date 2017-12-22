package hortonworks.hdf.sam.sdk.testcases.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)	
public class SAMTestCase implements Serializable {


	private static final long serialVersionUID = 5887370303797094957L;
	
	
	private Long id;
	private String name;
	private Long topologyId;
	private Long versionId;
	private Long timestamp;
	
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
	public Long getTopologyId() {
		return topologyId;
	}
	public void setTopologyId(Long topologyId) {
		this.topologyId = topologyId;
	}
	public Long getVersionId() {
		return versionId;
	}
	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}	
	
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	
	
	

}

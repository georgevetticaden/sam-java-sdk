package hortonworks.hdf.sam.sdk.app.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SAMAppSource implements Serializable {
	

	private static final long serialVersionUID = -2269276935316619256L;
	private Long id;
	private Long topologyId;
	private String name;
	
	private List<Map<String, Object>> outputStreams;

	


	public List<Map<String, Object>> getOutputStreams() {
		return outputStreams;
	}


	public void setOutputStreams(List<Map<String, Object>> outputStreams) {
		this.outputStreams = outputStreams;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getTopologyId() {
		return topologyId;
	}


	public void setTopologyId(Long topologyId) {
		this.topologyId = topologyId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}			
	
	
}

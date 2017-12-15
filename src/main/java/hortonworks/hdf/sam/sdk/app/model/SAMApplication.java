package hortonworks.hdf.sam.sdk.app.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SAMApplication implements Serializable {

	private static final long serialVersionUID = 3992776667834154129L;

	
	private Integer id;
	private String versionId;
	private String name;
	private String description;
	private String namespaceId;
	private String config;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public String getNamespaceId() {
		return namespaceId;
	}
	public void setNamespaceId(String namespaceId) {
		this.namespaceId = namespaceId;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	
	
	

}

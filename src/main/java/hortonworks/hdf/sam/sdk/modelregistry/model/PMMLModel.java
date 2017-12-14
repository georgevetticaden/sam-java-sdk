package hortonworks.hdf.sam.sdk.modelregistry.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)	
public class PMMLModel implements Serializable {

	private static final long serialVersionUID = -2260352324563440596L;

	
	private Integer id;
	private String name;
	private String pmml;
	private String uploadedFileName;
	private String nameSpace;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPmml() {
		return pmml;
	}
	public void setPmml(String pmml) {
		this.pmml = pmml;
	}
	public String getUploadedFileName() {
		return uploadedFileName;
	}
	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}		
		
	
	

}

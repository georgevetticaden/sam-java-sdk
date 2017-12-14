package hortonworks.hdf.sam.sdk.udf.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)	
public class SAMUDF implements Serializable {


	private static final long serialVersionUID = -7069125552540086175L;
	
	
	private Integer id;
	private String name;
	private String displayName;
	private String type;
	private String description;
	private String className;
	private String returnType;
	private boolean builtin;
	
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
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public boolean isBuiltin() {
		return builtin;
	}
	public void setBuiltin(boolean builtin) {
		this.builtin = builtin;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}		
	
}

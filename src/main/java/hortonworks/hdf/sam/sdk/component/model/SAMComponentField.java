package hortonworks.hdf.sam.sdk.component.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SAMComponentField implements Serializable {


	private static final long serialVersionUID = -2551650095894656661L;

	private String fieldName;
	private String defaultValue;
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	
	
}

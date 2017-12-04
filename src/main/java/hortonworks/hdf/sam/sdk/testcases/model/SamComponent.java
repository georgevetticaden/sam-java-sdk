package hortonworks.hdf.sam.sdk.testcases.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)																																					
public class SamComponent implements Serializable {


	private static final long serialVersionUID = -2793050965134299294L;
	private String componentName;
	private String eventId;
	
	Map<String, String> fieldsAndValues;

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public Map<String, String> getFieldsAndValues() {
		return fieldsAndValues;
	}

	public void setFieldsAndValues(Map<String, String> fieldsAndValues) {
		this.fieldsAndValues = fieldsAndValues;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	

}

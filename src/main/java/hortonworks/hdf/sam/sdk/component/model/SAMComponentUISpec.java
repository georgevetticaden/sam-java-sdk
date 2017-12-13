package hortonworks.hdf.sam.sdk.component.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SAMComponentUISpec implements Serializable {

	private static final long serialVersionUID = 883258339006600371L;
	
	private List<SAMComponentField> fields;

	public List<SAMComponentField> getFields() {
		return fields;
	}

	public void setFields(List<SAMComponentField> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}		

}

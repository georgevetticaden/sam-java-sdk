package hortonworks.hdf.sam.sdk.environment.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class SAMEnvironmentDetails implements Serializable {


	private static final long serialVersionUID = 7345780252819319059L;
	
	private SAMEnvironment namespace;
	private List<SAMServiceEnvironmentMapping> mappings;
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public SAMEnvironment getNamespace() {
		return namespace;
	}

	public void setNamespace(SAMEnvironment namespace) {
		this.namespace = namespace;
	}

	public List<SAMServiceEnvironmentMapping> getMappings() {
		return mappings;
	}

	public void setMappings(List<SAMServiceEnvironmentMapping> mappings) {
		this.mappings = mappings;
	}	
	
	

}

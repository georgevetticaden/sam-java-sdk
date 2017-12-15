package hortonworks.hdf.sam.sdk.app.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class SAMApplicationStatus implements Serializable {

	private static final long serialVersionUID = -2663517511158418890L;
	

	
	private String status;
	private Map<String, String> extra;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, String> getExtra() {
		return extra;
	}
	public void setExtra(Map<String, String> extra) {
		this.extra = extra;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}		
	
	

}

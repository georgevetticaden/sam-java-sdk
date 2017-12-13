package hortonworks.hdf.sam.sdk.component.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)	
public class SAMComponent implements Serializable {

	private static final long serialVersionUID = 8046379828651798682L;
	
	
	private Integer id;
	private String name;
	private String type;
	private String subType;
	private String bundleJar;
	private SAMComponentUISpec topologyComponentUISpecification;

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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getBundleJar() {
		return bundleJar;
	}
	public void setBundleJar(String bundleJar) {
		this.bundleJar = bundleJar;
	}
	
	public SAMComponentUISpec getTopologyComponentUISpecification() {
		return topologyComponentUISpecification;
	}
	public void setTopologyComponentUISpecification(
			SAMComponentUISpec topologyComponentUISpecification) {
		this.topologyComponentUISpecification = topologyComponentUISpecification;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}		
	

}

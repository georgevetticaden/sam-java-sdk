package hortonworks.hdf.sam.sdk.testcases.model;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TestCaseExecution implements Serializable{


	private static final long serialVersionUID = 623191821714979700L;
	
	/* Request Parameters */
	private Integer testCaseId;
	private Integer durationSecs;
	
	private Integer id;
	private boolean finished;
	private boolean success;
	
	
	public TestCaseExecution() {

	}

	public TestCaseExecution(Integer testCaseId, Integer durationSecs) {
		super();
		this.testCaseId = testCaseId;
		this.durationSecs = durationSecs;
	}
	
	public Integer getTestCaseId() {
		return testCaseId;
	}
	
	public void setTestCaseId(Integer testCaseId) {
		this.testCaseId = testCaseId;
	}
	
	public Integer getDurationSecs() {
		return durationSecs;
	}
	
	public void setDurationSecs(Integer durationSecs) {
		this.durationSecs = durationSecs;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}

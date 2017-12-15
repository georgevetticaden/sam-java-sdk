package hortonworks.hdf.sam.sdk.environment;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import hortonworks.hdf.sam.sdk.BaseSDKUtils;
import hortonworks.hdf.sam.sdk.environment.model.SAMEnvironment;


public class SAMEnvironmentSDKUtils extends BaseSDKUtils {

	public SAMEnvironmentSDKUtils(String restUrl) {
		super(restUrl);
	}

	public List<SAMEnvironment> getAllSAMEnvironments() {
		String url = constructRESTUrl("/catalog/namespaces");
		ResponseEntity<Map<String, List<SAMEnvironment>>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<SAMEnvironment>>>() {});
		return response.getBody().get("entities");
	}

	public SAMEnvironment getSAMEnvironment(String samDemoEnvName) {
		SAMEnvironment env = null;
		for (SAMEnvironment samEnv: getAllSAMEnvironments()) {
			if(samEnv.getName().equals(samDemoEnvName)) {
				env = samEnv;
				break;
			}
		}
		return env;
	}

}

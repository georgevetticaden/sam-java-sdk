# SAM JAVA REST SDK
This module provides a Java REST client/sdk for the Streaming Analytics Manager (SAM) REST APIs. The goal of these utilities to make it easier to consume the SAM REST apis. There are two types of SDK Utilities:

* SDK Utils - Provides Rest clients for the different types of entities in SAM (ServicePool, Environment, Application, TestCase, etc..)
* SDK Manager - Higher Level clients that provides cabpalities using a composition of the different type SDK utils. 

First look at the SDK Manager to see if it has the desired functionality and if not then look at the SDK Utils. 


## SAM SDK Managers

| SDK Manager Name| Description| Test Client  |
| ------------- |:-------------:| -----:|
| [SAMAppManager](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/app/manager/SAMAppManager.java) | Utilies to import, deploy and delete applications in SAM | [SAMAppManagerTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/app/manager/SAMAppManagerTest.java)|
| [SAMEnvironmentManager](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/environment/manager/SAMEnvironmentManager.java)     | Utilities to create and delete SAM enviornemnts      |   [SAMEnvironmentSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/environment/SAMEnvironmentSDKUtilsTest.java) |
| [SAMServicePoolManager](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/servicepool/manager/SAMServicePoolManager.java) | Utilities to create and delete service pools      |    [SAMServicePoolSDKUtilsTest](/) |
| [SAMTestCaseManager](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/testcases/manager/SAMTestCaseManager.java) |  Utilities to create SAM Test Case, run/execute a SAM Test Case and delete SAM Test Case| [SAMTestCaseSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/testcases/SAMTestCaseSDKUtilsTest.java)

## SAM SDK Utils
| SDK Util Name| Description| Test Client  |
| ------------- |:-------------:| -----:|
| [SAMAppSDKUtils](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/app/SAMAppSDKUtils.java) | Utilies to get details on a SAM App, import, delete, deploy, and kill SAM App  | [SAMAppSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/app/SAMAppSDKUtilsTest.java)|
| [SAMProcessorComponentSDKUtils](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/component/SAMProcessorComponentSDKUtils.java) | Utilities to upload, get details, and delete custom processors| [SAMProcessorComponentSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/component/SAMProcessorComponentSDKUtilsTest.java)
| [SAMSourceSinkComponentSDKUtils](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/component/SAMSourceSinkComponentSDKUtils.java)|Utilities to upload, get details, and delete custom sources and sinks| [SAMSourceSinkComponentSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/component/SAMSourceSinkComponentSDKUtilsTest.java)
| [SAMEnvironmentSDKUtils](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/environment/SAMEnvironmentSDKUtils.java) |Utilities to get details, create, map services and delete SAM Environments| [SAMEnvironmentSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/environment/SAMEnvironmentSDKUtilsTest.java)
| [SAMModelRegistrySDKUtils](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/modelregistry/SAMModelRegistrySDKUtils.java) |Utilities to get details, add and delete models in the Model Registry| [SAMModelRegistrySDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/modelregistry/SAMModelRegistrySDKUtilsTest.java)
| [SAMServicePoolSDKUtils](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/servicepool/SAMServicePoolSDKUtils.java) |Utilities to create, get details, import Ambari services and delete SAM Service Pools| [SAMServicePoolSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/servicepool/SAMServicePoolSDKUtilsTest.java)
| [SAMTestCaseSDKUtils](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/testcases/SAMTestCaseSDKUtils.java) |Utilities to get details, run, get test case executions, create and delete Test cases| [SAMTestCaseSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/testcases/SAMTestCaseSDKUtilsTest.java)
| [SAMUDFSDKUtils](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/main/java/hortonworks/hdf/sam/sdk/udf/SAMUDFSDKUtils.java) |Utilities to get details, upload, and delete custom UDFs| [SAMUDFSDKUtilsTest](https://github.com/georgevetticaden/sam-java-sdk/blob/master/src/test/java/hortonworks/hdf/sam/sdk/udf/SAMUDFSDKUtilsTest.java)
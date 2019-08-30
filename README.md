# Virtual Number Forwarding Lambda

A AWS lambda function for generating a call-plan for an incoming call from a virtual number. 

[![Build Status](https://dev.azure.com/open-source-bluurr-io/virtual-number-umbrella-project/_apis/build/status/bluurr.virtual-number-forwarding-lambda?branchName=master)](https://dev.azure.com/open-source-bluurr-io/virtual-number-umbrella-project/_build/latest?definitionId=1&branchName=master)

## Getting Started

This lambda allows a incoming phone call from a [Nexmo](https://nexmo.com) virtual phone number to be manipulated with the following behaviour(s):

- Voice instructions 
- Number Whitelisting

### Example Use Case

- Virtual Number for CV to reduce volume of recruiters calls.
- Burner number for short lived ads.

### Prerequisite

The library requires the following minimum versions

1. Java 8
2. [Maven 3+](https://maven.apache.org)
3. [Lombok](https://projectlombok.org)

#### Additional

- [Docker](https://www.docker.com) (For local running using AWS SAM)
- [AWS CLI](https://aws.amazon.com/cli) (For deployment to AWS)
- [cygwin](https://www.cygwin.com) (For deployment to AWS on Windows (Bash Required))

#### Services

- [AWS Account](https://aws.amazon.com/) (For deployment)
- [Nexmo Account](https://www.nexmo.com/) (For Virtual Number)

##### Nexmo

The Nexmo account will require an [application](https://dashboard.nexmo.com/voice/your-applications) to be set-up and a [number](https://dashboard.nexmo.com/buy-numbers) to be purchased and linked with the application.

The application needs the web-hooks for `Event URL` and `Answer URL` to be use HTTP `POST` and should point at the API Gateway (Custom domain or AWS provided URL). For example a custom URL may look like `https://dev.api.bluurr.io/call-event`
 
### Building

To build the lambda application run the following maven command.

```BASH
mvn clean verify
```

### Running Locally

The project can be ran locally in the following ways:

#### SAM tooling

Using [SAM](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html) the lambda function can be deployed in a AWS like environment on the local machine.

Full documentation can be found [here](./docs/RUN-LOCALLY.md)

#### Spring Boot Web Application

The application can be booted like a normal Spring Boot application ([Spring Docs](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html)).

This will start a web endpoint exposing the function at `http://localhost:8080/function` an example `POST` payload can be found [here](./src/test/resources/local/stack/basic-api-gateway-request-event.json).

More information can be found [here](https://cloud.spring.io/spring-cloud-function/multi/multi__functional_bean_definitions.html)

### AWS Deployment

This function can be deployed to the AWS lambda platform (it's recommended to take advantage of the free tier more information [here](https://aws.amazon.com/free/)). 

Full documentation can be found [here](./docs/AWS-DEPLOYMENT.md)

### Pipeline

A pipeline definition for building the lambda function can be found for the following.

#### Azure Pipeline

The following pipeline configuration found [here](./cicd/azure-pipeline/azure-build.yaml) can be imported directly into [azure pipelines](https://azure.microsoft.com/en-gb/services/devops/pipelines/) which will provide a full build of this lambda function.

## License
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with the License. You may obtain a copy of the License in the LICENSE file, or at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
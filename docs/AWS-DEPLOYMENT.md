# AWS Deployment

The project contains a number of scripts (found [here](./../cicd/aws)) to deploy the lambda function to AWS.

> It's recommended to use the [Runner Scripts](#Runner) for deployment of the AWS lambda function.

## Prerequisite

- [AWS CLI](https://aws.amazon.com/cli) (For deployment to AWS)
    - CLI needs to be configured with account details [documentation here](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html).
- [BASH](https://en.wikipedia.org/wiki/Bash_(Unix_shell))
    -  [cygwin](https://www.cygwin.com) (For deployment to AWS on Windows)
    
## Optional

- Understanding of AWS [CloudFormation](https://aws.amazon.com/cloudformation/)
- AWS [console access](https://aws.amazon.com/console/)

## Scripts

The scripts are broken down into the following folders.

- [common](#Common) (Common utility scripts used by the other script folders)
- [domain](#Domain) (Deploys a route53 domain against the deployed functions API gateway)
- [function](#Function) (Deploys the Lambda function with a API gateway)
- [runner](#Runner) (Pipeline structured scripts to deploy a complete set of scripts)

### Common

The common scripts are common utility scripts which are used by the other script.

> It's recommended to check out the [runner](#Runner), [function](#Function) or [domain](#Domain) scripts for example usage of the common scripts.

#### [aws-cf-upsert.sh](./../cicd/aws/common/aws-cf-upsert.sh)

The `aws-cf-upsert.sh` will perform an upsert (create/update-on-exist) operation for deployment of the requested CloudFormation template. The script will wait until the operation is completed/time-out/errors

#### [find-build-artifact.sh](./../cicd/aws/common/find-build-artifact.sh)

> *Note* this script is has the same outcome as [find-maven-build-artifact-name.sh](#[find-maven-build-artifact-name.sh]) but has the following advantages
> - Runs quicker.
> - Maven is not required to look-up the artifact name. 

The `find-build-artifact.sh` will attempt to find the the maven build artifact name for the fat-lambda code jar.

> Requires that the code has been built e.g `mvn verify`

#### [find-maven-build-artifact-name.sh](./../cicd/aws/common/find-maven-build-artifact-name.sh)

The `find-maven-build-artifact-name.sh` will attempt to find the the maven build artifact name for the fat-lambda code jar.

> Requires that the code has been built e.g `mvn verify`

#### [find-maven-directory.sh](./../cicd/aws/common/find-maven-directory.sh)

The `find-maven-directory.sh` will attempt to find the the maven project base directory by going up the file tree until it finds the first candidate.

#### [get-pipeline-parameter-names.sh](./../cicd/aws/common/get-pipeline-parameter-names.sh)

The `get-pipeline-parameter-names.sh` will return the name of all parameters (format `PARAMETER_`) that are currently available to the executing script.

#### [get-pipeline-parameter-values.sh](./../cicd/aws/common/get-pipeline-parameter-values.sh)

The `get-pipeline-parameter-values.sh` will return the name and value (Formatted as `parameterName=parameterValue`) of all parameters (format `PARAMETER_`) that are currently available to the executing script.

#### [process-cloud-template-parameters.sh](./../cicd/aws/common/process-cloud-template-parameters.sh)

The `process-cloud-template-parameters.sh` will return the processed version of a CloudFormation parameter template.

It's expected that the command will be called with the location of the CloudFormation parameter file and any additional parameters: 
e.g. `process-cloud-template-parameters.sh "example-cf-parameters.json ExampleParamater ExampleValue`. The `process-cloud-template-parameters.sh` will automatically have access to all parameters provided by [get-pipeline-parameter-values.sh](#get-pipeline-parameter-values.sh)

#### [process-cloud-template-parameters-and-save.sh](./../cicd/aws/common/process-cloud-template-parameters-and-save.sh)

The `process-cloud-template-parameters-and-save.sh` will process the parameters on CloudFormation parameter file and then save the output to a new file with the following name `${originalFileName}.cf-processed.${originalFileExt}`.

It's expected that the command will be called with the location of the CloudFormation parameter file and any additional parameter: 
e.g. `process-cloud-template-parameters-and-save.sh "example-cf-parameters.json ExampleParamater ExampleValue`. The `process-cloud-template-parameters-and-save.sh` will automatically have access to all parameters provided by [get-pipeline-parameter-values.sh](#get-pipeline-parameter-values.sh)

### Domain

The domain scripts contains the CloudFormation templates and scripts to deploys a route53 domain against the deployed functions API gateway.

#### Domain Certificate

The domain certificate is required before the api gateway domain can be set-up (unless an existing certificate exists and can be referenced).

> More information on AWS Certificate manager can be found [here](https://aws.amazon.com/certificate-manager/faqs/)

- [upsert-domain-certificate.sh](./../cicd/aws/domain/upsert-domain-certificate.sh) 
    - [CloudFormation Template](./../cicd/aws/domain/domain-certificate.cf.yml)
    - [CloudFormation Parameters](./../cicd/aws/domain/parameters/domain-certificate.parameters.json)

#### API Gateway Domain

To access the Lambda function via a managed Route53 domain name the following scripts can be ran.

> It's assumed that the route53 domain is already set-up and configured.
> This script assumes that the Lambda function is already deployed to AWS.


- [upsert-api-gateway-domain.sh](./../cicd/aws/domain/upsert-api-gateway-domain.sh) 
    - [CloudFormation Template](./../cicd/aws/domain/api-gateway-domain.cf.yml)
    - [CloudFormation Parameters](./../cicd/aws/domain/parameters/api-gateway-domain.parameters.json)

### Function

The function scripts provides the necessary scripts and templates for deploying a AWS Lambda function.

#### Lambda Code Bucket

The lambda code bucket is created to store the lambda code jar. See more AWS information [here](https://docs.aws.amazon.com/lambda/latest/dg/with-s3-example.html). 

1) [find-artifact-and-copy-lambda-code-to-s3.sh](./../cicd/aws/function/find-artifact-and-copy-lambda-code-to-s3.sh) - finds the artifact in the maven target to upload
2) [upsert-lambda-code-bucket.sh](./../cicd/aws/function/upsert-lambda-code-bucket.sh) - uploads the CloudFormation template for creating the lambda code bucket
    - [CloudFormation Template](./../cicd/aws/function/lambda-code-bucket-cf.yml)
    - [CloudFormation Parameters](./../cicd/aws/function/parameters/lambda-code-bucket.parameters.json)
3) [copy-lambda-code-to-bucket.sh](./../cicd/aws/function/copy-lambda-code-to-bucket.sh) - copies the requested artifact to the AWS S3 bucket.

#### Lambda Function

> To deploy the function the [Lambda Code Bucket](#Lambda Code Bucket) should previously be set-up.

The lambda function is deployed onto AWS using the following templates.

1) [upsert-lambda-function.sh](./../cicd/aws/function/upsert-lambda-function.sh) - uploads the CloudFormation template for deploying the lambda and API Gateway.
    - [CloudFormation Template](./../cicd/aws/function/lambda-function-cf.yml)
    - [CloudFormation Parameters](./../cicd/aws/function/parameters/lambda-function.parameters.json)

### Runner

The runner scripts provides a convenient method for deployment of the AWS lambda function.

#### [all-parameters.sh](./../cicd/aws/runner/all-parameters.sh)

The `all-parameters.sh` primary role is to provide the parameters for use within the CloudFormation templates.

For example `PARAMETER_ENVIRONMENT` will define the deployment environment and the resulting template names and [resource outputs](https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/outputs-section-structure.html).

> This file should be updated with the desired values before running the runner scripts.

#### [deploy-lambda-function.sh](./../cicd/aws/runner/deploy-lambda-function.sh)

The `deploy-lambda-function.sh` primary role is to perform the steps required to deploy the lambda code (including [API Gateway](https://aws.amazon.com/api-gateway/)) to the AWS environment.

> The lambda project must be built for this command to work e.g `mvn verify`

The following steps are performed within this script

- Load parameters and prepare CloudFormation templates.
- Ensure a [S3 bucket](https://aws.amazon.com/s3/) exists for uploading the lambda code jar.
- Uploads the lambda code jar to the S3 Bucket.
- Deploys the Lambda function including the API Gateway.

#### [deploy-domain-certificate.sh](./../cicd/aws/runner/deploy-domain-certificate.sh)

The `deploy-domain-certificate.sh` primary role is to create the required AWS certificate to enable a custom domain names to run against the API Gateway.

The following steps are performed within this script

- Load parameters and prepare CloudFormation templates.
- Deploys the CloudFormation to create the certificate.

#### [deploy-api-gateway-domain.sh](./../cicd/aws/runner/deploy-api-gateway-domain.sh)

the `deploy-api-gateway-domain.sh` primary role is to set-up a custom domain name against the API Gateway.

The following steps are performed within this script

- Load parameters and prepare CloudFormation templates.
- Deploys the CloudFormation Template
    - Set-up the API gateway with a custom name
    - Adds the Domain to Route53
    
## Parameters

The above scrips are powered by a number of configurable parameters. These parameters can be found in the [all-parameters.sh](#[all-parameters.sh])  and are used for configuring the runner scripts.

> Parameters are always prefixed with a `PARAMETER_`

> **The `all-parameters.sh` contains example default which should be updated depending on the use case**

### PARAMETER_ENVIRONMENT

The `PARAMETER_ENVIRONMENT` is used for defining which environment is used for deployment, e.g (test, prod). 

The following behaviours are controlled:

- CloudFormation names are prefixed.
- CloudFormation output are prefixed.
- S3 code bucket with be prefixed.
- API gateway stage will be named after the environment.

### PARAMETER_JAR_NAME

The `PARAMETER_JAR_NAME` is used for defining the name of the lambda code jar which should be deployed.

The following script can be used to locate the lambda code jar name [find-build-artifact.sh](#[find-build-artifact.sh]).

### PARAMETER_ROOT_DOMAIN

The `PARAMETER_ROOT_DOMAIN` should be the root level domain of the custom domain name, for example if the custom domain is `dev-apigateway.example.com` then the root would be `example.com`

The following behaviours are controlled:

- ROOT domain for certificate generation (Required for HTTPS support)

> More information on certificate management and creation can be found [here](https://aws.amazon.com/certificate-manager/)

### PARAMETER_SUB_DOMAINS

The `PARAMETER_SUB_DOMAINS` is used to add additional sub-domains in addition to the `PARAMETER_ROOT_DOMAIN`. These sub domains can use wildcard patterns for example `*.example.com` would support `dev-apigateway.example.com` due to the wildcard matcher.  

> It's optional to include sub domain for the certificate generation.

More information found [here](https://docs.aws.amazon.com/acm/latest/userguide/acm-certificate.html).

### PARAMETER_API_GATEWAY_DOMAIN

The `PARAMETER_API_GATEWAY_DOMAIN` is the domain/sub-domain for accessing the API-Gateway with a custom domain (more information [here](https://docs.aws.amazon.com/apigateway/latest/developerguide/how-to-custom-domains.html)).

For example if you have a route53 domain name of `example.com` the api gateway domain might be configured as `dev-apigateway.example.com`

### PARAMETER_ROUTE_53_HOSTED_ZONE

The `PARAMETER_ROUTE_53_HOSTED_ZONE` should refer to the name of the [hosted zone](https://docs.aws.amazon.com/Route53/latest/DeveloperGuide/AboutHZWorkingWith.html) within route53 which matches the target `PARAMETER_API_GATEWAY_DOMAIN` when using an API gateway [custom domain name](https://docs.aws.amazon.com/apigateway/latest/developerguide/how-to-custom-domains.html).

> This step assumes that a custom domain name is already hosted on route 53. If a custom domain needs to be set-up follow these steps [here](https://docs.aws.amazon.com/Route53/latest/DeveloperGuide/registrar.html) 

### PARAMETER_ACCESS_IP

The `PARAMETER_ACCESS_IP` is used to enable an additional ip in the whitelist when accessing the lambda function via the API-Gateway. 

More information on AWS resource policy can be found [here](https://docs.aws.amazon.com/apigateway/latest/developerguide/apigateway-resource-policies-create-attach.html#apigateway-resource-policies-create-attach-console) and policy examples found [here](https://docs.aws.amazon.com/apigateway/latest/developerguide/apigateway-resource-policies-examples.html)

> It's recommended to add your IP for development builds to validate connectivity.
> To find the correct IP to add use [whatismyipaddress](https://whatismyipaddress.com/).


### PARAMETER_DEFAULT_VOICE_ANSWER_TEXT

The `PARAMETER_DEFAULT_VOICE_ANSWER_TEXT` is used to define the default generated voice message which should be read back to if no other action overrides this behaviour.

More documentation on support voice formats can be found [here](https://developer.nexmo.com/voice/voice-api/guides/customizing-tts).
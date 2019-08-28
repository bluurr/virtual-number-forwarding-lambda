# AWS Deployment

The project contains a number of scripts (found [here](./../cicd/aws)) to deploy the lambda function to AWS.

> It's recommended to use the [Runner Scripts](#Runner) for deployment of the AWS lambda function.

## Prerequisite

- [AWS CLI](https://aws.amazon.com/cli) (For deployment to AWS)
    - CLI needs to be configured with account details [documentation here](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-configure.html).
- [BASH](https://en.wikipedia.org/wiki/Bash_(Unix_shell\))
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
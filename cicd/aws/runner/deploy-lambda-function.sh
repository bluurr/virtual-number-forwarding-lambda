#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

## Use Maven to find the jar name and version.
echo "Locating Maven build artifact"

## Find the maven artifact containing the lambda code base
mavenDir=$(find-maven-directory.sh)
buildArtifact=$(find-maven-build-artifact-name.sh "${mavenDir}")

echo "Prepaing cloud formation parameters"

## Parameters
export PARAMETER_ENVIRONMENT=dev
export PARAMETER_JAR_NAME=${buildArtifact}

##end of Parameters

##location of function parameters
functionParameterFolder='./../function/parameters/'

## Process the bucket parameters
process-cloud-template-parameters-and-save.sh "${functionParameterFolder}lambda-code-bucket.parameters.json"
process-cloud-template-parameters-and-save.sh "${functionParameterFolder}lambda-function.parameters.json"

## Create the lambda code bucket (this bucket holds the jar file)
echo "Creating AWS S3 bucket for storing Lambda jar artifact."

## Move to the folder holding the function scripts.
cd "./../function/" || exit

## upload the CF template.
./upsert-lambda-code-bucket.sh

echo "Uploading Lambda jar artifact to S3 bucket."

./find-artifact-and-copy-lambda-code-to-s3.sh

## Upload CF template to create/update the Lambda function in AWS

echo "Deploying Lambda function to AWS"

./upsert-lambda-function.sh

## End of the deployment process.
echo "AWS Lambda Deployment finished"
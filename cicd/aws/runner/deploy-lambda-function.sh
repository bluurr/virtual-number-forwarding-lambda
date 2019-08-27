#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

echo "Prepaing parameters"

## Pepare all the parameters.
. ./all-parameters.sh ## Export all the required parameters

## Move to common folder to process parameters.
cd "./../common/" || exit

## Debugging only, used for showing which values are going to be used within the pipeline
echo "Using parameters:"

./get-pipeline-parameter-values.sh ##Prints parameter name and value.

##location of function parameters
functionParameterFolder='./../function/parameters/'

./process-cloud-template-parameters-and-save.sh "${functionParameterFolder}lambda-code-bucket.parameters.json"
./process-cloud-template-parameters-and-save.sh "${functionParameterFolder}lambda-function.parameters.json"

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
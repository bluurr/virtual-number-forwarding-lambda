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
functionParameterFolder='./../domain/parameters/'

./process-cloud-template-parameters-and-save.sh "${functionParameterFolder}domain-certificate.parameters.json"

## Deploy the domain templates to AWS
echo "Deploy Domain Certificate."
echo "Please check AWS console to confirm domain ownership validation"

## Move to the folder holding the domain scripts.
cd "./../domain/" || exit

./upsert-api-gateway-domain.sh
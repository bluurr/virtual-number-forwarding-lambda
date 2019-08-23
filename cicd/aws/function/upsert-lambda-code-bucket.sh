#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

stackName="virtual-number-lambda-bucket-${PARAMETER_ENVIRONMENT}"
templateFile=lambda-code-bucket-cf.yml
parameterFile=./parameters/cf-processed-lambda-code-bucket-parameters.json

aws-cf-upsert.sh "${stackName}" ${templateFile} --parameters file://${parameterFile}
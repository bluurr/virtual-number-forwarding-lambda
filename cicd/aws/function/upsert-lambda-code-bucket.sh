#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

stackName="${PARAMETER_ENVIRONMENT}-virtual-number-lambda-bucket"
templateFile=lambda-code-bucket-cf.yml
parameterFile=./parameters/lambda-code-bucket.parameters.cf-processed.json

aws-cf-upsert.sh "${stackName}" ${templateFile} --parameters file://${parameterFile}
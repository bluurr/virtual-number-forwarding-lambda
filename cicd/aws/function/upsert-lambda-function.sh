#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

stackName="${PARAMETER_ENVIRONMENT}-virtual-number-lambda-function"
templateFile=lambda-function-cf.yml
parameterFile=./parameters/lambda-function.parameters.cf-processed.json

aws-cf-upsert.sh "${stackName}" ${templateFile} --parameters file://${parameterFile} \
    --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND
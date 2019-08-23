#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

stackName="virtual-number-lambda-function-${PARAMETER_ENVIRONMENT}"
templateFile=lambda-function-cf.yml
parameterFile=./parameters/cf-processed-lambda-function-parameters.json

aws-cf-upsert.sh "${stackName}" ${templateFile} --parameters file://${parameterFile} \
    --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND
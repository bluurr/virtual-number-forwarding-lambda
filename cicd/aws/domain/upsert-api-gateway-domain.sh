#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

stackName="virtual-number-api-gateway-domain-${PARAMETER_ENVIRONMENT}"
templateFile=api-gateway-domain.cf.yml
parameterFile=./parameters/api-gateway-domain.parameters.cf-processed.json

aws-cf-upsert.sh "${stackName}" ${templateFile} --parameters file://${parameterFile}
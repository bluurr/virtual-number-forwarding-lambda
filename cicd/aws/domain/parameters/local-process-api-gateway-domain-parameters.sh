#!/usr/bin/env bash
PATH='./../../common/':$PATH ## Adds the common script folder to the path

parameterFile=api-gateway-domain.parameters.json ## main parameter file to use.

parameters=('PARAMETER_API_GATEWAY_DOMAIN' "test-gateway.api.bluurr.io" 'PARAMETER_HOSTED_ZONE' "bluurr.io.")
processedParameterFile=$(process-cloud-template-parameters.sh ${parameterFile} "${parameters[@]}")

## Print to console and save to parameter file.
echo "${processedParameterFile}"
echo "${processedParameterFile}" > "cf-processed-${parameterFile}"
echo "Parameter processing completed."
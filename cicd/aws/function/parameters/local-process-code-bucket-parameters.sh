#!/usr/bin/env bash
PATH='./../../common/':$PATH ## Adds the common script folder to the path

parameterFile=lambda-code-bucket-parameters.json ## main parameter file to use.

echo "Processing parameter file: ${parameterFile}."

parameters=('PARAMETER_JAR_NAME' "${buildArtifact}")
processedParameterFile=$(process-cloud-template-parameters.sh ${parameterFile} "${parameters[@]}")

## Print to console and save to parameter file.
echo "${processedParameterFile}"
echo "${processedParameterFile}" > "cf-processed-${parameterFile}"
echo "Parameter processing completed."




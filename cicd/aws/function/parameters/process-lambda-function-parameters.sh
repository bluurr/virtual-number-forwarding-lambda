#!/usr/bin/env bash
PATH='./../../common/':$PATH ## Adds the common script folder to the path

parameterFile=lambda-function-parameters.json ## main parameter file to use.

echo "Processing parameter file: ${parameterFile}."
echo 'Finding maven build artifact name and version.'

mavenDir=$(find-maven-directory.sh)
buildArtifact=$(find-maven-build-artifact-name.sh ${mavenDir})
echo ${buildArtifact}

echo "Build artifact found ${buildArtifact}."

parameters=('lambdaJarName' ${buildArtifact})
processedParameterFile=$(process-cloud-template-parameters.sh ${parameterFile} ${parameters[@]})
echo ${processedParameterFile} > "cf-processed-"${parameterFile}
echo 'Parameter processing completed.'



#!/usr/bin/env bash
parameterFile=lambda-function-parameters.json ## main parameter file to use.

echo "Processing parameter file: ${parameterFile}."
echo 'Finding maven build artifact name and version.'

mavenDir=$(./../../common/find-maven-directory.sh)
buildArtifact=$(./../../common/find-maven-build-artifact-name.sh ${mavenDir})
echo ${buildArtifact}

echo "Build artifact found ${buildArtifact}."

parameters=('lambdaJarName' ${buildArtifact})
processedParameterFile=$(./../../common/process-cloud-template-parameters.sh ${parameterFile} ${parameters[@]})
echo ${processedParameterFile} > "cf-processed-"${parameterFile}

echo 'Parameter processing completed.'




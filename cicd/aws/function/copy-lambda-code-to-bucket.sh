#!/usr/bin/env bash
buildArtifactLocation=$1

if [[ ${buildArtifactLocation} ]]
then
    aws s3 cp "${buildArtifactLocation}" "s3://${PARAMETER_ENVIRONMENT}-virtual-number-lambda-s3-bucket"
else
    echo "Build artifact location must be provided."
fi
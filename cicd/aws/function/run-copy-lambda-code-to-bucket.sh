#!/usr/bin/env bash
buildArtifactLocation=$1

if [[ ${buildArtifactLocation} ]]
then
    aws s3 cp ${buildArtifactLocation} s3://lambda-code-virtual-number
else
    echo "Build artifact location must be provided."
fi
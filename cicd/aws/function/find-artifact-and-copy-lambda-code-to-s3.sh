#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

mavenDir=$(find-maven-directory.sh)
buildArtifact=$(find-maven-build-artifact-name.sh "${mavenDir}")

if [[ ${buildArtifact} ]]
then
    buildArtifactLocation=${mavenDir}/target/${buildArtifact}
    copy-lambda-code-to-bucket.sh "${buildArtifactLocation}"
else
    echo "Unable to find maven build artifact."
fi
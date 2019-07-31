#!/usr/bin/env bash
mavenDir=$(./../common/find-maven-directory.sh)
buildArtifact=$(./../common/find-maven-build-artifact-name.sh ${mavenDir})

if [[ ${buildArtifact} ]]
then
    buildArtifactLocation=${mavenDir}/target/${buildArtifact}
    ./run-copy-lambda-code-to-bucket.sh ${buildArtifactLocation}
else
    echo "Unable to find maven build artifact."
fi
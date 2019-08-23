#!/usr/bin/env bash
mavenDir=$1
buildArtifact=

if [[ ${mavenDir} ]]
then
    ## Run maven to find the artifact id and project version.
    buildArtifact=$(mvn -f "${mavenDir}"/pom.xml -q -Dexec.executable="echo" -Dexec.args='${project.artifactId}-${project.version}.jar' exec:exec)
fi

if [[ ${buildArtifact} ]]
then
    echo "${buildArtifact}"
fi
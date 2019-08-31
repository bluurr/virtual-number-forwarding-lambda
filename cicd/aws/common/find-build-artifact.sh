#!/usr/bin/env bash
mavenDir=$1
## Find the main jar file and return just the file without the path
lambdaJarName="$(find "${mavenDir}" -type f ! -name "original*" -name "*.jar" -printf '%f\n')"
echo "${lambdaJarName}"
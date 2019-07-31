#!/usr/bin/env bash
mavenDir=.
searchDepth=5
found=false

## Find the root folder for the maven project
while [[ ${searchDepth} -gt 0 ]]
do
    if [[ $(find ${mavenDir} -maxdepth 1 -name 'pom.xml' -type f) ]]
    then
        searchDepth=0 ##Root folder found.
        found=true
    else
      searchDepth=$(($searchDepth - 1)) ## Moved the search to the next index
      mavenDir=${mavenDir}.'/../'
    fi
done

if [[ ${found} = true ]]
then
    echo ${mavenDir}
fi
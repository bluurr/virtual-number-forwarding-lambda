#!/usr/bin/env bash
args=("${@}")
parameterFile=${args[0]}
parameters=("${args[@]:1}") ## Skip first parameter as this is the parameter file.

echo "Processing parameter file ${parameterFile}."

## Run the processor
processedFileData=$(process-cloud-template-parameters.sh "${parameterFile}" "${parameters[@]}")

## Debugging
echo "${processedFileData}"

##split the file path+name and the file extension
parameterFilePathAndName="${parameterFile%.*}"
parameterFileExt="${parameterFile##*.}"

## Create the formation for example ./parameterFodler/name-of-file.cf-processed.json
processParamaterFileName="${parameterFilePathAndName}.cf-processed.${parameterFileExt}"

###Save the content into a processed version of the file
echo "${processedFileData}" > "${processParamaterFileName}"

## End of script
echo "Parameter processing completed, output file: ${processParamaterFileName}."
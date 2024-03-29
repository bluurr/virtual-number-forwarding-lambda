#!/usr/bin/env bash
args=("${@}")
parameterFile=${args[0]}
parameters=("${args[@]:1}") ## Skip first parameter as this is the parameter file.

## Extract environment/pipeline variables to apply to the parameter file.
mapfile -t pipelineParameterNames < <(./get-pipeline-parameter-names.sh)

for parameterName in "${pipelineParameterNames[@]}"
do
    parameterValue=${!parameterName}
    parameters+=("${parameterName}" "${parameterValue}")
done

## Work out the amount of parameters to apply
parameterSize=${#parameters[@]}
output=""

## Read our file and process each line for matching parameters
while read -r line || [[ -n ${line} ]] ##Fully read the file.
do
    processedOutput=${line}
    parameterOffset=0

    ## Start of parameter processing
    while [[ ${parameterOffset} -lt ${parameterSize} ]]
    do
        ## array is formatted (parameterName, parameterValue, ...)
        parameterName=${parameters[${parameterOffset}]}
        parameterValue=${parameters[${parameterOffset} + 1]}
        ##Replace parameter in the format ${NAME} with value.
        processedOutput=${processedOutput//\$\{"${parameterName}"\}/"${parameterValue}"}
        parameterOffset=$((parameterOffset + 2))
    done

     output+=${processedOutput}

    ## End of parameter processing
done < "${parameterFile}"

## Response data of the input file
echo "${output}"





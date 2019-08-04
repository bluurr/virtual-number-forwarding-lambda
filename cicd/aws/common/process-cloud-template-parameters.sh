#!/usr/bin/env bash
args=(${@})
parameterFile=${args[0]}
parameters=(${args[@]:1})

parameterSize=${#parameters[@]}
output=""

while read line || [[ -n ${line} ]] ##Fully read the file.
do
    processedOutput=${line}
    parameterOffset=0

    ## Start of parameter processing
    while [[ ${parameterOffset} -lt ${parameterSize} ]]
    do
        ## array is formatted (parameterName, parameterValue, ...)
        parameterName=${parameters[${parameterOffset}]}
        parameterValue=${parameters[${parameterOffset} + 1]}
        processedOutput=$(echo ${processedOutput} | sed 's/${'${parameterName}'}/'${parameterValue}'/g')
        parameterOffset+=2 ## Move the index along
    done

     output+=${processedOutput}

    ## End of parameter processing
done < ${parameterFile}

echo ${output}





#!/usr/bin/env bash

## compgen https://unix.stackexchange.com/questions/151118/understand-compgen-builtin-command
compgen -v | while read -r PARAM_NAME; do
    ## Filter to variables which are support.
    if [[ ${PARAM_NAME} == 'PARAMETER_'* ]]
    then
        echo "${PARAM_NAME}"
    fi
done
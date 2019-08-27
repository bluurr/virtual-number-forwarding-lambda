#!/usr/bin/env bash
mapfile -t paramaterNames < <(./get-pipeline-parameter-names.sh)
## Print all the parameters and their values
for parameterName in "${paramaterNames[@]}"
do
  echo "${parameterName}=${!parameterName}"
done
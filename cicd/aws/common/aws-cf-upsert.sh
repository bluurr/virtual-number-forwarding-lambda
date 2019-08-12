#!/usr/bin/env bash
args=(${@})
stackName=${args[0]}
templateFileLocation=${args[1]}
parameters=(${args[@]:2})

stackStatus=$(aws cloudformation describe-stacks --stack-name ${stackName})

if [[ ${stackStatus} ]];
then
   aws cloudformation update-stack --template-body file://${templateFileLocation} --stack-name ${stackName} ${parameters[@]}
else
    aws cloudformation create-stack --template-body file://${templateFileLocation} --stack-name ${stackName} ${parameters[@]}
fi
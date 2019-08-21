#!/usr/bin/env bash
args=(${@})
stackName=${args[0]}
templateFileLocation=${args[1]}
parameters=(${args[@]:2})

stackStatus=$(aws cloudformation describe-stacks --stack-name ${stackName})

if [[ ${stackStatus} ]];
then
   ## 2>&1 redirect cloud formation error to standard console (https://www.brianstorti.com/understanding-shell-script-idiom-redirect/)
   updateStatus=$(aws cloudformation update-stack --template-body file://${templateFileLocation} --stack-name ${stackName} ${parameters[@]} 2>&1)
   ## Skip waiting if no updates to watch for
   if [[ ${updateStatus} != *"No updates are to be performed"* ]];
   then
        aws cloudformation wait stack-update-complete --stack-name ${stackName}
   fi
else
    aws cloudformation create-stack --template-body file://${templateFileLocation} --stack-name ${stackName} ${parameters[@]}
    aws cloudformation wait stack-create-complete --stack-name ${stackName}
fi


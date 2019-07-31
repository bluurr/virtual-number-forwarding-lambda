#!/usr/bin/env bash
stackName=virtual-number-lambda-function
templateFile=lambda-function-cf.yml
parameterFile=./parameters/lambda-function-parameters.json

## Check if we need to perform a create or update request
aws cloudformation describe-stacks --stack-name $stackName

if [ $? -eq 0 ];
then
   aws cloudformation update-stack --template-body file://${templateFile} --parameters file://${parameterFile} --stack-name $stackName --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND
else
    aws cloudformation create-stack --template-body file://${templateFile} --parameters file://${parameterFile} --stack-name $stackName --capabilities CAPABILITY_IAM CAPABILITY_AUTO_EXPAND
fi
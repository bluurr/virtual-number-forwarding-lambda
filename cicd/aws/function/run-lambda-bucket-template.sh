#!/usr/bin/env bash
stackName=virtual-number-lambda-bucket
templateFile=lambda-code-bucket-cf.yml

## Check if we need to perform a create or update request
aws cloudformation describe-stacks --stack-name $stackName

if [ $? -eq 0 ];
then
   aws cloudformation update-stack --template-body file://$templateFile --stack-name $stackName
else
    aws cloudformation create-stack --template-body file://$templateFile --stack-name $stackName
fi
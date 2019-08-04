#!/usr/bin/env bash
stackName=domain-certificate-stack
templateFile=domain-certificate.cf.yml
parameterFile=./parameters/domain-certificate.parameters.json

## Check if we need to perform a create or update request
aws cloudformation describe-stacks --stack-name $stackName

if [ $? -eq 0 ];
then
   aws cloudformation update-stack --template-body file://$templateFile --parameters file://${parameterFile} --stack-name $stackName
else
    aws cloudformation create-stack --template-body file://$templateFile --parameters file://${parameterFile} --stack-name $stackName
fi
#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

stackName=domain-certificate-stack
templateFile=domain-certificate.cf.yml
parameterFile=./parameters/domain-certificate.parameters.json

aws-cf-upsert.sh ${stackName} ${templateFile} --parameters file://${parameterFile}
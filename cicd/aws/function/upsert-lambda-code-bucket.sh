#!/usr/bin/env bash
PATH='./../common/':$PATH ## Adds the common script folder to the path

stackName=virtual-number-lambda-bucket
templateFile=lambda-code-bucket-cf.yml

aws-cf-upsert.sh ${stackName} ${templateFile}
## Use Maven to find the jar name and version.
echo "Locating Maven build artifact"

## Find the maven artifact containing the lambda code base
mavenDir=$(find-maven-directory.sh)
buildArtifact=$(find-build-artifact.sh "${mavenDir}")

echo "Exporting parameters"

export PARAMETER_ENVIRONMENT=dev
export PARAMETER_JAR_NAME=${buildArtifact}

## For use with domain set-up only
export PARAMETER_ROOT_DOMAIN="bluurr.io"
export PARAMETER_SUB_DOMAINS="*.bluurr.io,*.api.bluurr.io"

export PARAMETER_API_GATEWAY_DOMAIN="dev-gateway.api.bluurr.io"
export PARAMETER_ROUTE_53_HOSTED_ZONE="bluurr.io."

## Suggested to use your public IP (https://whatismyipaddress.com/)
export PARAMETER_ACCESS_IP=""

## Function driven parameters
## Change this parameter to configure the response a non-white listed caller will recieve.
export PARAMETER_DEFAULT_VOICE_ANSWER_TEXT="<speak>Hello, Thank you for calling. I'm currently not available to take this call.</speak>"
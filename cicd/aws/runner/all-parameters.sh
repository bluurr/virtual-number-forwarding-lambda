## Use Maven to find the jar name and version.
echo "Locating Maven build artifact"

## Find the maven artifact containing the lambda code base
mavenDir=$(find-maven-directory.sh)
buildArtifact=$(find-maven-build-artifact-name.sh "${mavenDir}")

echo "Exporting parameters"

export PARAMETER_ENVIRONMENT=dev
export PARAMETER_JAR_NAME=${buildArtifact}

## For use with domain set-up only
export PARAMETER_API_GATEWAY_DOMAIN="dev-gateway.api.bluurr.io"
export PARAMETER_ROUTE_53_HOSTED_ZONE="bluurr.io"
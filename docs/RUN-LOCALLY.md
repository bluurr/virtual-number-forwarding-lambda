# Run Locally

When developing the lambda function it's useful to run and debug the code locally. This can be achieved with the following tooling.

## SAM

Using [SAM](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/what-is-sam.html) the lambda function can be deployed in a AWS like environment as a docker container.

### Prerequisite

- [AWS CLI](https://aws.amazon.com/cli) (For deployment to AWS)
- [SAM](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-install.html)
- [Docker](https://www.docker.com)

### Running

To run the function with SAM:

1) Ensure the project is built and packaged e.g. `mvn verify`
2) Run corresponding script found [here](./../local).
3) Wait until the script has deployed the lambda to docker
4) Attach a remote debugger with the following configurations:
   - IDE: `port=5005 host=localhost`
   - VM command: `-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005`
5) Debug code and execution will complete

The sam function will use the cloud formation template found [here](./../src/test/resources/local/stack/template.yml) and the event information found [here](./../src/test/resources/local/stack/basic-api-gateway-request-event.json)
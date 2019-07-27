@echo off
set scriptLocation=%cd%
set stackLocation=../target/test-classes/local/stack
sam local invoke -d 5005 --event "%stackLocation%/basic-api-gateway-request-event.json" -t "%stackLocation%/template.yml"



#!/usr/bin/env bash
cd "$(dirname "$0")" ## CD to this current folder.
cd ../target/test-classes/local/stack
sam local invoke -d 5005 --event basic-api-gateway-request-event.json

package io.bluurr.forward.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EntryPoint {

  public APIGatewayProxyResponseEvent process(final APIGatewayProxyRequestEvent event) {
    log.error("Hello world running event!");
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    response.setBody("Hello world!");
    response.setStatusCode(200);
    return response;
  }
}

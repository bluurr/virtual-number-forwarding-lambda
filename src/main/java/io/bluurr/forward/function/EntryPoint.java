package io.bluurr.forward.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

@Slf4j
public class EntryPoint {

 public Message<APIGatewayProxyResponseEvent> process(final Message<APIGatewayProxyRequestEvent> message) {
    APIGatewayProxyResponseEvent response = process(message.getPayload());
    return new GenericMessage<>(response);
  }

  public APIGatewayProxyResponseEvent process(final APIGatewayProxyRequestEvent event) {
    log.error("Hello world running event!");
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    response.setBody("Hello world!");
    response.setStatusCode(200);
    return response;
  }
}

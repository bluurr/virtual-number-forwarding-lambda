package io.bluurr.forward.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.nexmo.client.incoming.CallEvent;
import com.nexmo.client.voice.ncco.Ncco;
import io.bluurr.forward.provider.CallPlanProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

@Slf4j
@AllArgsConstructor
public class VirtualNumberFunction {

  private final CallPlanProvider callPlanProvider;

  public APIGatewayProxyResponseEvent handle(final APIGatewayProxyRequestEvent event) {
    try {
      CallEvent callEvent = CallEvent.fromJson(event.getBody());
      Ncco response = getCallPlanForEvent(callEvent);
      return createGatewayReply(response);
    } catch(final Exception err) {
      log.error("Error with event: {}", event); // capture information that is lost in the stack trace.
      throw new IllegalStateException("Unable to handle call event request", err);
    }
  }

  private APIGatewayProxyResponseEvent createGatewayReply(final @Nullable Ncco callActions) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
        .withStatusCode(200);

    if(callActions != null) {
      response.setBody(callActions.toJson());
    }

    log.info("Responded with: {}", response);
    return response;
  }

  @Nullable
  private Ncco getCallPlanForEvent(final CallEvent event) {
    if (isAnsweredEvent(event)) {
      return callPlanProvider.onAnswered(event.getFrom());
    }
    return null;
  }

  private boolean isAnsweredEvent(final CallEvent event) {
    return event != null && event.getStatus() == null && event.getConversationUuid() != null;
  }
}

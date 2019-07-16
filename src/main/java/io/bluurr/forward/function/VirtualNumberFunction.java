package io.bluurr.forward.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.nexmo.client.incoming.CallEvent;
import com.nexmo.client.voice.VoiceName;
import com.nexmo.client.voice.ncco.Ncco;
import com.nexmo.client.voice.ncco.TalkAction;
import com.nexmo.client.voice.servlet.NccoResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class VirtualNumberFunction {

  public APIGatewayProxyResponseEvent handle(final APIGatewayProxyRequestEvent event) {
    CallEvent callEvent = toStatusEvent(event.getBody());
    Ncco response = handleCallStage(callEvent);
    return replyForSuccess(response);
  }

  private APIGatewayProxyResponseEvent replyForSuccess(final @Nullable Ncco callActions) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
    String body = callActions != null ? callActions.toJson() : "";
    response.setBody(body);
    response.setStatusCode(200);
    return response;
  }

  @Nullable
  private Ncco handleCallStage(final CallEvent event) {
    log.info("EVENT:", event);
    switch (event.getStatus()) {
      case ANSWERED:
        return createEventForStarted(event);

      default:
        log.warn("Skipping event of type: {}", event.getStatus());
        return null;
    }
  }

  private Ncco createEventForStarted(final CallEvent event) {
    TalkAction talk =
        new TalkAction.Builder("Hello, Thank you for calling.").voiceName(VoiceName.BRIAN).build();
    return new Ncco(talk);
  }

  private CallEvent toStatusEvent(final String jsonValue) {
    return CallEvent.fromJson(jsonValue);
  }
}

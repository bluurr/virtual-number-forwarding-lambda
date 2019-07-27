package io.bluurr.forward.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.nexmo.client.incoming.CallEvent;
import com.nexmo.client.voice.VoiceName;
import com.nexmo.client.voice.ncco.Ncco;
import com.nexmo.client.voice.ncco.TalkAction;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

@Slf4j
@AllArgsConstructor
public class VirtualNumberFunction {

  public APIGatewayProxyResponseEvent handle(final APIGatewayProxyRequestEvent event) {
    try {
      CallEvent callEvent = CallEvent.fromJson(event.getBody());
      Ncco response = getCallPlanForEvent(callEvent);
      return createGatewayReply(response);
    } catch(final Exception err) {
      log.error("Error with event: {}", event);
      throw new IllegalStateException("Unable to handle call event request", err);
    }
  }

  private APIGatewayProxyResponseEvent createGatewayReply(final @Nullable Ncco callActions) {
    APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent()
        .withStatusCode(200);

    if(callActions != null) {
      response.setBody(callActions.toJson());
    }

    log.info("responded with: {}", response);
    return response;
  }

  @Nullable
  private Ncco getCallPlanForEvent(final CallEvent event) {
    if (isAnsweredEvent(event)) {
      return getAnsweredCallPlan();
    }

    log.warn("Call event information: {}", event);
    return null;
  }

  private Ncco getAnsweredCallPlan() {
    //TODO: make this speech load from a configuration file.
    TalkAction talk = new TalkAction.Builder("<speak>Hello, Thank you for calling. I'm currently not available to take this call. " +
        "Please drop me an email at <sub alias=\"chris.bath@blur.com\">chris.bath@bluurr.com</sub>. That is Chris dot Bath at <break strength='weak' />B <break strength='weak' />L <break strength='weak' />U " +
        "<break strength='weak' />U <break strength='weak'/>R <break strength='weak' />R dot com. Thank you.</speak>")
        .voiceName(VoiceName.BRIAN)
        .build();
    return new Ncco(talk);
  }

  private boolean isAnsweredEvent(final CallEvent event) {
    return event != null && event.getStatus() == null && event.getConversationUuid() != null;
  }
}

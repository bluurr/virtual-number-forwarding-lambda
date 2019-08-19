package io.bluurr.forward.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.nexmo.client.voice.VoiceName;
import com.nexmo.client.voice.ncco.Ncco;
import com.nexmo.client.voice.ncco.TalkAction;
import io.bluurr.forward.provider.CallPlanProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static io.bluurr.forward.test.resource.CallEventDataStore.createAnsweredEvent;
import static io.bluurr.forward.test.resource.CallEventDataStore.createEventForStatus;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class VirtualNumberFunctionTest {

  @Mock
  private CallPlanProvider provider;

  @Spy
  @InjectMocks
  private VirtualNumberFunction virtualNumberFunction;

  @Test
  void shouldThrowErrorForInvalidCallEventContent() {
    APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody("INVALID BODY");

    IllegalStateException err = assertThrows(IllegalStateException.class,
        () -> virtualNumberFunction.handle(event));

    assertThat(err.getMessage(), is("Unable to handle call event request"));
  }

  @Test
  void shouldThrowErrorForNullCallEventContent() {
    APIGatewayProxyRequestEvent event = new APIGatewayProxyRequestEvent().withBody(null);

    IllegalStateException err = assertThrows(IllegalStateException.class,
        () -> virtualNumberFunction.handle(event));

    assertThat(err.getMessage(), is("Unable to handle call event request"));
  }

  @Test
  void shouldResponseWithNoActionWhenNotAnsweredStatus() {
    String event = createEventForStatus("ringing");
    APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(event);

    APIGatewayProxyResponseEvent result = virtualNumberFunction.handle(apiEvent);
    assertThat(result, notNullValue());
    assertThat(result.getStatusCode(), is(HttpStatus.OK.value()));
    assertThat(result.getBody(), nullValue());
  }

  @Test
  void shouldResponseWithNoActionWhenBlankStatus() {
    String event = createEventForStatus("");
    APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(event);

    APIGatewayProxyResponseEvent result = virtualNumberFunction.handle(apiEvent);
    assertThat(result, notNullValue());
    assertThat(result.getStatusCode(), is(HttpStatus.OK.value()));
    assertThat(result.getBody(), nullValue());
  }

  @Test
  void shouldResponseWithActionWhenAnsweredStatus() {
    TalkAction talk = new TalkAction.Builder("Example response talk here")
        .voiceName(VoiceName.BRIAN)
        .build();

    doReturn(new Ncco(talk)).when(provider).onAnswered(Mockito.any());

    String event = createAnsweredEvent();
    APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(event);

    APIGatewayProxyResponseEvent result = virtualNumberFunction.handle(apiEvent);
    assertThat(result, notNullValue());
    assertThat(result.getStatusCode(), is(HttpStatus.OK.value()));

    assertThat(result.getBody(), notNullValue());
    assertThat(result.getBody(), containsString("Example response talk here"));
    assertThat(result.getBody(), containsString("Brian"));
  }
}
package io.bluurr.forward.function;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static io.bluurr.forward.test.resource.CallEventDataStore.createAnsweredEvent;
import static io.bluurr.forward.test.resource.CallEventDataStore.createEventForStatus;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class VirtualNumberFunctionTest {

  @Spy
  private VirtualNumberFunction virtualNumberFunction = new VirtualNumberFunction();

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
    String event = createAnsweredEvent();
    APIGatewayProxyRequestEvent apiEvent = new APIGatewayProxyRequestEvent().withBody(event);

    APIGatewayProxyResponseEvent result = virtualNumberFunction.handle(apiEvent);
    assertThat(result, notNullValue());
    assertThat(result.getStatusCode(), is(HttpStatus.OK.value()));
    assertThat(result.getBody(), notNullValue());
  }
}

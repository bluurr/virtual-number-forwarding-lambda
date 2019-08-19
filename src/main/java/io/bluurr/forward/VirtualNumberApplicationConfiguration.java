package io.bluurr.forward;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.bluurr.forward.function.VirtualNumberFunction;
import io.bluurr.forward.model.TalkDialogue;
import io.bluurr.forward.provider.CallPlanProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class VirtualNumberApplicationConfiguration {

  private static final String DEFAULT_VOICE_RESPONSE = "default.voice.response";

  Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> functionEntryPoint(final ApplicationContext context) {
    VirtualNumberFunction function = new VirtualNumberFunction(callPlanProvider(context));
    return function::handle;
  }

  private CallPlanProvider callPlanProvider(final ApplicationContext context) {
    TalkDialogue defaultTalkDialogue = getDefaultTalkDialogue(context);
    return new CallPlanProvider(defaultTalkDialogue);
  }

  private TalkDialogue getDefaultTalkDialogue(final ApplicationContext context) {
    String message = context.getEnvironment().getProperty(DEFAULT_VOICE_RESPONSE + ".text");
    return new TalkDialogue().setText(message);
  }

  @Bean
  @ConfigurationProperties(prefix = DEFAULT_VOICE_RESPONSE)
  TalkDialogue defaultTalkDialogue() {
    return new TalkDialogue();
  }
}

package io.bluurr.forward;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.bluurr.forward.function.VirtualNumberFunction;
import io.bluurr.forward.model.TalkDialogue;
import io.bluurr.forward.provider.CallPlanProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class VirtualNumberApplication {

  public static void main(final String[] args) {
    SpringApplication.run(VirtualNumberApplication.class, args);
  }

  @Bean("function")
  Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createEntryFunction(final CallPlanProvider provider) {
    return new VirtualNumberFunction(provider)::handle;
  }

  @Bean
  CallPlanProvider callPlanProvider(final TalkDialogue defaultTalkDialogue) {
    return new CallPlanProvider(defaultTalkDialogue);
  }

  @Bean
  @ConfigurationProperties(prefix="default.voice.response")
  TalkDialogue defaultTalkDialogue() {
    return new TalkDialogue();
  }
}

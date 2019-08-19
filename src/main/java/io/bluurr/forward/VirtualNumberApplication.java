package io.bluurr.forward;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class VirtualNumberApplication extends VirtualNumberFunctionalApplication {

  public static void main(final String[] args) {
    SpringApplication.run(VirtualNumberApplication.class, args);
  }

  @Bean("function")
  Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createEntryFunction(final VirtualNumberApplicationConfiguration configuration,
                                                                                          final ApplicationContext context) {
    return configuration.functionEntryPoint(context);
  }
}

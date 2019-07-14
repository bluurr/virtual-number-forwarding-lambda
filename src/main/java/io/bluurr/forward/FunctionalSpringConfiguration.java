package io.bluurr.forward;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.bluurr.forward.function.EntryPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class FunctionalSpringConfiguration  {

  public static void main(final String[] args) {
    SpringApplication.run(FunctionalSpringConfiguration.class, args);
  }

  @Bean("lambdaFunction")
  public Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> apply() {
    return new EntryPoint()::process;
  }
}

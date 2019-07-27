package io.bluurr.forward;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.bluurr.forward.function.VirtualNumberFunction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class VirtualNumberApplication {

  public static void main(final String[] args) {
    SpringApplication.run(VirtualNumberApplication.class, args);
  }

  @Bean("function")
  Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createEntryFunction() {
    return new VirtualNumberFunction()::handle;
  }
}

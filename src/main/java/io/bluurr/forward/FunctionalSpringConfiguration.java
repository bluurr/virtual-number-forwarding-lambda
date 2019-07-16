package io.bluurr.forward;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.bluurr.forward.function.VirtualNumberFunction;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

import java.util.function.Function;

@SpringBootConfiguration
public class FunctionalSpringConfiguration implements ApplicationContextInitializer<GenericApplicationContext> {

  public static void main(final String[] args) {
    FunctionalSpringApplication.run(FunctionalSpringConfiguration.class, args);
  }

  @Override
  public void initialize(GenericApplicationContext context) {
    context.registerBean("demo", FunctionRegistration.class,
        () -> new FunctionRegistration<>(createEntryFunction(new VirtualNumberFunction()))
            .type(FunctionType.from(APIGatewayProxyRequestEvent.class).to(APIGatewayProxyResponseEvent.class)));
  }

  private Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createEntryFunction(final VirtualNumberFunction function) {
    return function::handle;
  }
}

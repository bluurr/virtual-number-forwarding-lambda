package io.bluurr.forward;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

@SpringBootConfiguration
public class VirtualNumberFunctionalApplication implements ApplicationContextInitializer<GenericApplicationContext> {

  public static void main(final String[] args) {
    FunctionalSpringApplication.run(VirtualNumberFunctionalApplication.class, args);
  }

  @Override
  public void initialize(GenericApplicationContext context) {
    VirtualNumberApplicationConfiguration configuration = new VirtualNumberApplicationConfiguration();
    context.registerBean("function", FunctionRegistration.class, () ->
        new FunctionRegistration<>(configuration.functionEntryPoint(context))
            .type(FunctionType.from(APIGatewayProxyRequestEvent.class).to(APIGatewayProxyResponseEvent.class))
    );
  }



}

package io.bluurr.forward;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.bluurr.forward.function.VirtualNumberFunction;
import io.bluurr.forward.model.TalkDialogue;
import io.bluurr.forward.provider.CallPlanProvider;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

import java.util.function.Function;

@SpringBootConfiguration
public class VirtualNumberFunctionalApplication implements ApplicationContextInitializer<GenericApplicationContext> {

  public static void main(final String[] args) {
    FunctionalSpringApplication.run(VirtualNumberFunctionalApplication.class, args);
  }

  @Override
  public void initialize(GenericApplicationContext context) {
    context.registerBean("function", FunctionRegistration.class, () ->
        new FunctionRegistration<>(createEntryFunction(context))
            .type(FunctionType.from(APIGatewayProxyRequestEvent.class).to(APIGatewayProxyResponseEvent.class))
    );
  }

  private CallPlanProvider callPlanProvider(final ApplicationContext context) {
    TalkDialogue defaultTalkDialogue = getDefaultTalkDialogue(context);
    return new CallPlanProvider(defaultTalkDialogue);
  }

  private Function<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> createEntryFunction(final ApplicationContext context) {
    VirtualNumberFunction function = new VirtualNumberFunction(callPlanProvider(context));
    return function::handle;
  }

  private TalkDialogue getDefaultTalkDialogue(final ApplicationContext context) {
    String message = context.getEnvironment().getProperty("default.callPlan.message");
    return new TalkDialogue().setText(message);
  }

}

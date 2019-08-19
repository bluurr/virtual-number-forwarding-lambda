package io.bluurr.forward;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@FunctionalSpringBootTest(classes = VirtualNumberFunctionalApplication.class)
@ExtendWith(SpringExtension.class)
class VirtualNumberFunctionalApplicationIT {

  @Autowired
  private ApplicationContext context;

  @Test
  void shouldValidateContextLoads() {
    assertThat(context, notNullValue());
  }

  @Test
  void shouldValidateFunctionBeanLoaded() {
    assertThat(context, notNullValue());
    assertThat(context.getBean("function"), notNullValue());
  }

}

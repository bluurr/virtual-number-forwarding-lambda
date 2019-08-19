package io.bluurr.forward;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(classes = VirtualNumberApplication.class)
@ExtendWith(SpringExtension.class)
class VirtualNumberApplicationIT {

  @Autowired
  private ApplicationContext context;

  @Test
  void shouldValidateContextLoads() {
    assertThat(context, notNullValue());
  }
}

package io.bluurr.forward.function;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VirtualNumberFunctionTest {

  @Spy
  private VirtualNumberFunction virtualNumberFunction = new VirtualNumberFunction();

  @Test
  void shouldThrowErrorForInvalidCallEventContent() {
  }
}

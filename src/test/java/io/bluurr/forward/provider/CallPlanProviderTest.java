package io.bluurr.forward.provider;

import com.nexmo.client.voice.VoiceName;
import com.nexmo.client.voice.ncco.Ncco;
import com.nexmo.client.voice.ncco.TalkAction;
import io.bluurr.forward.model.TalkDialogue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(MockitoExtension.class)
class CallPlanProviderTest {

  @Spy
  private TalkDialogue dialogue = new TalkDialogue().setText("Placeholder testing response text!");

  @Spy
  @InjectMocks
  private CallPlanProvider callPlanProvider;

  @Test
  void shouldReturnPlaceHolderText() {
    Ncco result = callPlanProvider.onAnswered("447700900000");
    assertThat(result, notNullValue());
    assertThat(result.getActions(), hasSize(1));
    assertThat(result.getActions(), hasItem(instanceOf(TalkAction.class)));
    assertThat(result.getActions(), hasItem(hasProperty("text", is("Placeholder testing response text!"))));
    assertThat(result.getActions(), hasItem(hasProperty("voiceName", is(VoiceName.BRIAN))));
  }
}

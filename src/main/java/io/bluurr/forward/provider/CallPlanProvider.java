package io.bluurr.forward.provider;

import com.nexmo.client.voice.VoiceName;
import com.nexmo.client.voice.ncco.Ncco;
import com.nexmo.client.voice.ncco.TalkAction;
import io.bluurr.forward.model.TalkDialogue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CallPlanProvider {

  private final TalkDialogue defaultTalkResponse;

  public Ncco onAnswered(final String incomingTelephoneNumber) {
    if (isWhitelistNumber(incomingTelephoneNumber)) {
      log.info("Whitelisted number: {}", incomingTelephoneNumber);
    }
    TalkAction talk = new TalkAction.Builder(defaultTalkResponse.getText())
        .voiceName(VoiceName.BRIAN)
        .build();
    return new Ncco(talk);
  }

  private boolean isWhitelistNumber(final String incomingTelephoneNumber) {
    return false; //TODO: implement whitelist feature.
  }
}


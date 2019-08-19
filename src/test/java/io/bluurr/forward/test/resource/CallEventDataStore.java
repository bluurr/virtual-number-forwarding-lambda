package io.bluurr.forward.test.resource;

import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;

@UtilityClass
public class CallEventDataStore {

  public static String createEventForStatus(final String status) {
    String json = loadFile("samples/nexmo/webhook/incoming/call-event.json");
    return json.replace("{{status}}", status);
  }

  public static String createAnsweredEvent() {
    return loadFile("samples/nexmo/webhook/incoming/answered-call-event.json");
  }
  private static String loadFile(final String file) {
    try (InputStream eventJson = new ClassPathResource(file).getInputStream()) {
      return IOUtils.toString(eventJson, Charset.defaultCharset());
    } catch (final IOException err) {
      throw new UncheckedIOException("Unable to load call event", err);
    }
  }
}

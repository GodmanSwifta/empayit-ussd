package com.swifta.ussd.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.xml.transform.TransformerObjectSupport;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpLoggingUtils extends TransformerObjectSupport {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpLoggingUtils.class);

  private static final String NEW_LINE = System.getProperty("line.separator");

  private static final String[] keywords = {"password", "pin"};

  private HttpLoggingUtils() {}

  public static void logMessage(String id, WebServiceMessage webServiceMessage) {
    try {
      ByteArrayTransportOutputStream byteArrayTransportOutputStream =
          new ByteArrayTransportOutputStream();
      webServiceMessage.writeTo(byteArrayTransportOutputStream);

      String httpMessage = new String(byteArrayTransportOutputStream.toByteArray());
      LOGGER.info(NEW_LINE + "----------------------------" + NEW_LINE + id + NEW_LINE
          + "----------------------------" + NEW_LINE + maskSecret(httpMessage) + NEW_LINE);
    } catch (Exception e) {
      LOGGER.error("Unable to log HTTP message.", e);
    }
  }

  public static String maskSecret(String payload) {
    for (String word : keywords) {
      payload = maskSecretForKey(word, payload);
    }
    return payload;
  }

  private static String maskSecretForKey(String key, String payload) {
    Pattern p = Pattern.compile("\"" + key + "\"\\s?:\\s?\"[a-zA-Z0-9]*\"");
    Matcher m = p.matcher(payload);
    return m.replaceAll("\"" + key + "\": \"*****\"");
  }
}

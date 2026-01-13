package io.github.rubeneekhof.lastfm.infrastructure.http;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

public class ApiSignatureGenerator {

  public static String generate(String method, Map<String, String> params, String apiSecret) {
    TreeMap<String, String> sortedParams = new TreeMap<>(params);
    sortedParams.put("method", method);

    StringBuilder signatureString = new StringBuilder();
    for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
      signatureString.append(entry.getKey()).append(entry.getValue());
    }
    signatureString.append(apiSecret);

    return md5(signatureString.toString());
  }

  private static String md5(String input) {
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte b : digest) {
        sb.append(String.format("%02x", b));
      }
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5 algorithm not available", e);
    }
  }
}

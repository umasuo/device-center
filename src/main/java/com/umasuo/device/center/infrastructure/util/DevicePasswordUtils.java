package com.umasuo.device.center.infrastructure.util;

import com.umasuo.device.center.infrastructure.exception.GeneratePasswordException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Device password utils.
 */
public final class DevicePasswordUtils {

  /**
   * Private default constructor.
   */
  private DevicePasswordUtils(){}
  /**
   * Get password.
   *
   * @param publicKey
   * @return
   */
  public static String getPassword(String publicKey) {
    try {
      byte[] md5 = MessageDigest.getInstance("MD5").digest(publicKey.getBytes(StandardCharsets.UTF_8));
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < md5.length; i++) {
        sb.append(Integer.toString((md5[i] & 0xff) + 0x100, 16).substring(1));
      }
      return sb.toString().substring(7, 23);
    } catch (Exception e) {
      throw new GeneratePasswordException("Generate device password failed.");
    }
  }
}

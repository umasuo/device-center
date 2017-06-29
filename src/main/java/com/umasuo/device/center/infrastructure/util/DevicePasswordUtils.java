package com.umasuo.device.center.infrastructure.util;

import java.security.MessageDigest;

/**
 * Created by umasuo on 17/6/29.
 */
public class DevicePasswordUtils {

  public static String getPassword(String publicKey) {
    try {
      byte[] md5 = MessageDigest.getInstance("MD5").digest(publicKey.getBytes());
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < md5.length; i++) {
        sb.append(Integer.toString((md5[i] & 0xff) + 0x100, 16).substring(1));
      }
      return sb.toString().substring(7, 23);
    } catch (Exception e) {
      return null;
    }
  }
}

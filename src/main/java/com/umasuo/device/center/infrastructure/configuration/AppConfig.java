package com.umasuo.device.center.infrastructure.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * App config.
 */
@Configuration
@Data
public class AppConfig {

  /**
   * Token expires time.
   */
  @Value("${jwt.secret:QWERTYUIOPASDFGHJKLZXCVBNM}")
  public String secret;

  /**
   * Token expires time.
   */
  @Value("${admin.authentication.expires:7200000}")
  public long expiresIn;
}

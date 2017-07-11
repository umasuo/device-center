package com.umasuo.device.center.infrastructure.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by umasuo on 17/6/27.
 */
@Configuration
@Data
public class AppConfig {

  /**
   * message broker's host.
   */
  @Value("${messagebroker.host:127.0.0.1}")
  public String msgBrokerHost;

  /**
   * message broker's port
   */
  @Value("${messagebroker.port:1883}")
  public long msgBrokerPort;

  /**
   * super user's username for message broker.
   */
  @Value("${messagebroker.username:umasuo}")
  public String username;

  /**
   * super user's password for message broker.
   */
  @Value("${messagebroker.password:password}")
  public String password;

}

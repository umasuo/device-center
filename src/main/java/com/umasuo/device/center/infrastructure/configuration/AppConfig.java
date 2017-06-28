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

  @Value("${message-broker.host:127.0.0.1}")
  public String msgBrokerHost;

  @Value("${message-broker.port:1883}")
  public long msgBrokerPort;

  @Value("${message-broker.username:umasuo}")
  public String username;

  @Value("${message-broker.password:password}")
  public String password;
}

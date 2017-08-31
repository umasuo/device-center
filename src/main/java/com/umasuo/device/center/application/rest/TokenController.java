package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.service.TokenApplication;
import com.umasuo.device.center.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token controller.
 */
@RestController
public class TokenController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TokenController.class);

  /**
   * The Token service.
   */
  @Autowired
  private transient TokenApplication tokenApplication;

  /**
   * Create token string.
   *
   * @param userId the user id
   * @return the string
   */
  @PostMapping(value = Router.DEVICE_TOKEN)
  public String createToken(@RequestHeader("userId") String userId) {
    LOGGER.info("Enter. userId: {}.", userId);

    String token = tokenApplication.createToken(userId);

    LOGGER.info("Exit. token: {}.", token);

    return token;
  }
}

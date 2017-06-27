package com.umasuo.device.center.application.service;

import com.umasuo.exception.NotExistException;
import com.umasuo.exception.ParametersException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Davis on 17/6/27.
 */
@Service
public class TokenApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(TokenApplication.class);

  /**
   * Validation code expire time;
   */
  private static final long EXPIRE_TIME = 3 * 60 * 1000L;

  /**
   * Token prefix.
   */
  private static final String USER_DEVICE_TOKEN_PREFIX = "user:device:token:";

  /**
   * Redis time util.
   */
  private static final TimeUnit TIME_UTIL = TimeUnit.MILLISECONDS;

  /**
   * Token length.
   */
  private static final int TOKEN_LENGTH = 7;

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * 新建用户配网token，如果该用户的配网token已存在，则返回已存在的token，反之则生成一个新的token。
   *
   * @param userId
   * @return
   */
  public String createToken(String userId) {
    LOG.debug("Enter. userId: {}.", userId);

    String token = (String) redisTemplate.opsForValue().get(userId);
    if (StringUtils.isBlank(token)) {
      token = RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
      String key = USER_DEVICE_TOKEN_PREFIX + userId;
      redisTemplate.opsForValue().set(key, token, EXPIRE_TIME, TIME_UTIL);
    }

    LOG.debug("Exit. token: {}.", token);
    return token;
  }

  /**
   * 检查用户token是否匹配，如果匹配则正常运行，反之则抛出对应异常。
   *
   * @param userId
   * @param token
   */
  public void validateToken(String userId, String token) {
    LOG.debug("Enter. userId: {}, token: {}.");

    String key = USER_DEVICE_TOKEN_PREFIX + userId;
    String requestToken = (String) redisTemplate.opsForValue().get(key);
    if (StringUtils.isBlank(requestToken)) {
      LOG.debug("Can not find token by user: {}.", userId);
      throw new NotExistException("Token not exist or expire.");
    }

    if (!requestToken.equals(token)) {
      LOG.debug("Token not match, input token: {}, request token: {}.", token, requestToken);
      throw new ParametersException("Token not match");
    }

    redisTemplate.delete(key);
  }
}

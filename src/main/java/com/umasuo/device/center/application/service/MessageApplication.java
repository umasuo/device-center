package com.umasuo.device.center.application.service;

import com.umasuo.device.center.infrastructure.configuration.AppConfig;
import com.umasuo.device.center.infrastructure.exception.GeneratePasswordException;
import com.umasuo.device.center.infrastructure.util.DevicePasswordUtils;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/27.
 */
@Service
public class MessageApplication {
  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(MessageApplication.class);

  private transient AppConfig appConfig;

  private transient MQTT mqtt;

  private transient BlockingConnection publisher;

  private static final String USERNAME_PREFIX = "mqtt_user:";
  /**
   * redis ops.
   */
  @Autowired
  private transient StringRedisTemplate redisTemplate;

  /**
   * 初始化和message broker的连接.
   *
   * @param appConfig 系统配置
   */
  @Autowired
  public MessageApplication(AppConfig appConfig) {
    this.appConfig = appConfig;
    mqtt = new MQTT();
    mqtt.setUserName(appConfig.getUsername());
    mqtt.setPassword(appConfig.getPassword());
    publisher = mqtt.blockingConnection();
    try {
      publisher.connect();
      logger.info("Connect to message broker: " + appConfig.getMsgBrokerHost());
    } catch (Exception e) {
      logger.error("Connect message broker failed.", e);
    }
  }

  /**
   * 发布消息.
   *
   * @param topic   topic，如果是设备的topic，则为topicID
   * @param payload 内容
   * @param qos     消息发送等级（0，1，2）
   * @param retain  是否保持在broker上
   */
  public void publish(final String topic, final byte[] payload, final QoS qos, final boolean
      retain) {
    logger.debug("Enter. topic: {}, payload: {}, qos: {}, retain: {}.", topic, new String
        (payload), qos, retain);
    try {
      publisher.publish(topic, payload, qos, retain);
    } catch (Exception e) {
      logger.error("publish message failed.", e);
    }
  }

  /**
   * @param deviceId
   * @param userId
   */
  public void publish(String deviceId, String userId) {
    logger.debug("Enter. deviceId: {}, userId: {}.", deviceId, userId);
    // 组织每个用户的App只订阅自己的那一个topic,对topic内容的解析交给程序自己
    String topic = "user:" + userId;
    //根据一定规则定义message的内容,例如前5个字符表示code，后面表示这次传递的内容.
    String message = "10001:" + deviceId;
    publish(topic, message.getBytes(), QoS.AT_LEAST_ONCE, false);
  }

  /**
   * 在redis中添加可以连接上来的用户名和密码.
   *
   * @param username  用户名为设备的ID
   * @param publicKey password 为下发到设备的token
   */
  public void addDeviceUser(String username, String publicKey) {
    logger.debug("Add broker user: {}.", username);

    String password = DevicePasswordUtils.getPassword(publicKey);
    if (password == null) {
      throw new GeneratePasswordException("Generate device password failed.");
    }

    BoundHashOperations setOperations = redisTemplate.boundHashOps(USERNAME_PREFIX + username);
    //TODO MQTT 的的密码需要采用加密模式
    setOperations.put("password", password);
  }


}

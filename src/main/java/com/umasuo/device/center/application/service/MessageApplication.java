package com.umasuo.device.center.application.service;

import com.umasuo.device.center.infrastructure.configuration.AppConfig;
import com.umasuo.device.center.infrastructure.exception.GeneratePasswordException;
import com.umasuo.device.center.infrastructure.exception.SubDeviceTopicException;
import com.umasuo.device.center.infrastructure.util.DevicePasswordUtils;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.Message;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/27.
 */
@Service
public class MessageApplication implements CommandLineRunner {
  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(MessageApplication.class);

  private transient AppConfig appConfig;

  private transient MQTT mqtt;

  private transient BlockingConnection connection;

  private static final String USERNAME_PREFIX = "mqtt_user:";
  private static final String DEVICE_TOPIC_SUB_PREFIX = "device/sub/";
  private static final String DEVICE_TOPIC_PUB_PREFIX = "device/pub/";

  private List<Topic> topics = new ArrayList<>();
  /**
   * redis ops.
   */
  private transient StringRedisTemplate redisTemplate;


  private transient DeviceMessageHandler deviceMessageHandler;


  /**
   * 初始化和message broker的连接.
   *
   * @param appConfig 系统配置
   */
  @Autowired
  public MessageApplication(StringRedisTemplate redisTemplate,
                            DeviceMessageHandler deviceMessageHandler,
                            AppConfig appConfig
  ) {
    this.appConfig = appConfig;
    this.redisTemplate = redisTemplate;
    this.deviceMessageHandler = deviceMessageHandler;
    redisTemplate.boundHashOps(USERNAME_PREFIX + appConfig.getUsername()).put("password",
        appConfig.getPassword());

    mqtt = new MQTT();
    mqtt.setUserName(appConfig.getUsername());
    mqtt.setPassword(appConfig.getPassword());
    connection = mqtt.blockingConnection();
    try {
      connection.connect();
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
      connection.publish(topic, payload, qos, retain);
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
    //TODO 根据一定规则定义message的内容,例如前5个字符表示code，后面表示这次传递的内容.
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

    try {
      String password = DevicePasswordUtils.getPassword(publicKey);
      if (password == null) {
        throw new GeneratePasswordException("Generate device password failed.");
      }
      topics.add(new Topic(DEVICE_TOPIC_PUB_PREFIX + username, QoS.AT_LEAST_ONCE));
      connection.subscribe(topics.toArray(new Topic[topics.size()]));
      BoundHashOperations setOperations = redisTemplate.boundHashOps(USERNAME_PREFIX + username);
      //TODO MQTT 的的密码需要采用加密模式
      setOperations.put("password", password);
    } catch (Exception e) {
      logger.error("Subscribe device topic failed. deviceId : {}", username);
      throw new SubDeviceTopicException("Subscribe device topic failed. deviceId : " + username);
    }

  }

  /**
   * Service 启动时自动接受
   *
   * @param args
   * @throws Exception
   */
  @Override
  public void run(String... args) throws Exception {
    logger.info("start process data.");
    while (true) {
      Message message = connection.receive();
      if (message != null) {
        String topic = message.getTopic();//从这里可以获得deviceID，
        String deviceId = topic.substring(DEVICE_TOPIC_PUB_PREFIX.length() - 1);
        String payload = new String(message.getPayload());//从这里可以获取device上发的命令和数据

        boolean handlerResult = deviceMessageHandler.handler(deviceId, payload);
        if (handlerResult) {
          message.ack();
        }
      }
    }
  }
}

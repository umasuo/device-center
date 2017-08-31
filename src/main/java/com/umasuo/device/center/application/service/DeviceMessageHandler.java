package com.umasuo.device.center.application.service;

import com.umasuo.device.center.application.dto.DeviceMessage;
import com.umasuo.device.center.application.dto.Session;
import com.umasuo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * Device message handler, used to handler messages that device send to cloud.
 */
@Service
public class DeviceMessageHandler {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeviceMessageHandler.class);

  /**
   * Device message max delay.
   */
  private static final long SECOND_OF_MAX_DELAY = 10 * 60;//每个设备的时差超过10分钟就需要重置时间了

  /**
   * device service.
   */
  @Autowired
  private transient SessionApplication sessionApplication;

  /**
   * handler all the messages that device send to broker.
   * 1 通过deviceId 获取其public key
   * 2 通过public key解密content.
   * 3 根据格式解析content
   * 4 执行对应的服务
   * 5 正确处理之后，返回true，否则返回false，以供再次处理消息
   *
   * @param deviceId deviceId
   * @return handler result
   */
  public boolean handler(String deviceId, byte[] payload) {

    String contentStr = new String(payload, StandardCharsets.UTF_8);
    LOGGER.debug("Enter. deviceId: {}, content: {}.", deviceId, contentStr);


//    String publicKey = session.getDevice().getPublicKey();
    //todo 通过public key解密content
    DeviceMessage message = JsonUtils.deserialize(contentStr, DeviceMessage.class);
//    DeviceMessage.Content content = message.getContent();


    //时间戳为UTC 0 的时间戳
    //todo 检查设备的时区，根据时区来进行时间的对比
    long curTime = System.currentTimeMillis() / 1000;//当前时间,精确到秒
    long delay = Math.abs(curTime - message.getT());
    if (delay > SECOND_OF_MAX_DELAY) {
      // TODO: 17/7/20 下发时间同步指令和数据
      return true;
    }

    //处理消息
    Session session = sessionApplication.getSession(deviceId);
    switch (message.getType()) {
      case 1:
        return processFunction(message, session);
      case 2:
        return processData(message, session);
      default:
        return false;
    }

  }

  /**
   * 处理功能型消息.主要是针对不同类型的设备的不同功能增加处理.
   */
  public boolean processFunction(DeviceMessage message, Session session) {

    // 控制型的指令由APP直接处理
    return false;
  }

  /**
   * 处理数据型消息.主要就是存储数据.
   */
  public boolean processData(DeviceMessage message, Session session) {
    //todo save data with the data definition, 目前不支持.
    return false;
  }

}

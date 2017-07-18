package com.umasuo.device.center.application.service;

import com.umasuo.device.center.application.dto.DeviceMessage;
import com.umasuo.device.center.infrastructure.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by umasuo on 17/6/30.
 * Device message handler, used to handler messages that device send to cloud.
 */
@Service
public class DeviceMessageHandler {

  private static final Logger logger = LoggerFactory.getLogger(DeviceMessageHandler.class);

  private static final long SECOND_OF_MAX_DELAY = 12 * 60 * 60;

  /**
   * handler all the messages that device send to broker.
   *
   * @param deviceId deviceId
   * @param content  the real content
   * @return handler result
   */
  public boolean handler(String deviceId, String content) {
    logger.debug("Enter. deviceId: {}, content: {}.", deviceId, content);
    //1 通过deviceId 获取其public key
    DeviceMessage message = JsonUtils.deserialize(content, DeviceMessage.class);
    //2 通过public key解密content
    //3 根据格式解析content
    //4 执行对应的服务
    //5 正确处理之后，返回true，否则返回false，以供再次处理消息

    //时间差超过12小时的消息，直接丢弃(不同时区和UTC时间的差最大为12小时)
    long curTime = System.currentTimeMillis();//当前时间
    long delay = Math.abs(curTime - message.getT());
    if (delay > SECOND_OF_MAX_DELAY) {
      return true;
    }

    //处理消息

    try {
      switch (message.getType()) {
        case 1:
          processFunction(message);
        case 2:
          processData(message);
      }
      logger.debug("Exit. result: {}.", true);
      return true;
    } catch (Exception e) {
      logger.warn("Process message failed: {}.", message);
      logger.debug("Exit. result: {}.", false);
      return false;
    }

  }

  /**
   * 处理功能型消息.主要是针对不同类型的设备的不同功能增加处理.
   */
  public void processFunction(DeviceMessage message) {
    //todo 有一条消息是需要处理的：设备状态消息
  }

  /**
   * 处理数据型消息.主要就是存储数据.
   */
  public void processData(DeviceMessage message) {
    //todo save data with the data definition
  }
}

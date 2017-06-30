package com.umasuo.device.center.application.service;

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

  /**
   * handler all the messages that device send to broker.
   *
   * @param deviceId deviceId
   * @param content  the real content
   * @return handler result
   */
  public boolean handler(String deviceId, String content) {

    //1 通过deviceId 获取其public key
    //2 通过public key解密content
    //3 根据格式解析content
    //4 执行对应的服务
    //5 正确处理之后，返回true，否则返回false，以供再次处理消息

    return true;
  }
}

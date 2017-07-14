package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.DeviceMessage;
import com.umasuo.device.center.application.service.MessageApplication;
import com.umasuo.device.center.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by umasuo on 17/7/14.
 */
@RestController
@CrossOrigin
public class MessageController {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

  /**
   * The Token service.
   */
  @Autowired
  private MessageApplication messageApplication;

  /**
   * 发送消息给设备，消息可以是控制指令，也可以是下发的数据.
   *
   * @param userId   用户ID
   * @param deviceId 设备ID
   * @param message  消息
   * @return
   */
  @PostMapping(value = Router.DEVICE_MESSAGE)
  public void sendMessageToDevice(@RequestHeader("userId") String userId,
                                  @PathVariable("id") String deviceId,
                                  @RequestBody DeviceMessage message) {
    logger.info("Enter. userId: {}, deviceId: {}, message: {}.", userId, deviceId, message);
    message.setDeviceId(deviceId);

    messageApplication.publish(deviceId, userId, message);

    logger.info("Exit.");
  }

  /**
   * 用于使用HTTP的方式接受设备发送过来的消息.
   *
   * @param userId   String，userId
   * @param deviceId String deviceId
   * @return
   */
  @GetMapping(value = Router.DEVICE_MESSAGE)
  public List<DeviceMessage> getDeviceMessage(@RequestHeader("userId") String userId,
                                              @RequestParam String deviceId) {

    return null;
  }
}

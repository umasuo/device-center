package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.DeviceActivateResult;
import com.umasuo.device.center.application.dto.DeviceData;
import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.application.service.DeviceApplication;
import com.umasuo.device.center.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Created by umasuo on 17/6/5.
 */
@RestController
@CrossOrigin
public class DeviceController {

  private final static Logger logger = LoggerFactory.getLogger(DeviceController.class);

  @Autowired
  private transient DeviceApplication deviceApplication;

  /**
   * 激活设备。
   *
   * @param userId
   * @param draft
   * @return
   */
  @PostMapping(Router.DEVICE_CENTER_ROOT)
  public DeviceActivateResult activate(@RequestHeader("userId") String userId,
                                       @RequestBody @Valid DeviceDraft draft) {
    logger.info("Enter. deviceDraft: {}.", draft);

    DeviceActivateResult result = deviceApplication.activate(draft, userId);

    logger.info("Exit. result:{}.", result);
    return result;
  }

  /**
   * 解除用户与设备的绑定关系。
   *
   * @param userId
   * @param deviceId
   */
  @DeleteMapping(Router.DEVICE_CENTER_WITH_ID)
  public void unbind(@RequestHeader("userId") String userId,
                     @PathVariable("id") String deviceId) {
    logger.info("Enter. userId: {}, deviceId: {}.", userId, deviceId);

    deviceApplication.unbind(userId, deviceId);

    logger.info("Exit.");
  }

  /**
   * get device by device id.
   *
   * @param id          String device id
   * @param userId      the user id
   * @param developerId the developer id
   * @return DeviceView device
   */
  @GetMapping(Router.DEVICE_CENTER_WITH_ID)
  public DeviceView getDevice(@PathVariable String id,
                              @RequestHeader(required = false) String userId,
                              @RequestHeader String developerId) {
    logger.info("Enter. deviceId: {}.", id);

    DeviceView view = deviceApplication.getByDeviceId(id, developerId, userId);

    logger.info("Exit. deviceView: {}.", view);
    return view;
  }

  /**
   * 获取用户在某个开发者下的所有设备.
   *
   * @param userId      String
   * @param developerId String in header
   * @return list of device view
   */
  @GetMapping(value = Router.DEVICE_CENTER_ROOT, params = {"userId"})
  public List<DeviceView> getAllDeviceByUser(@RequestParam String userId,
                                             @RequestHeader String developerId) {
    logger.info("Enter. userId: {}, developerId: {}.", userId, developerId);

    List<DeviceView> views = deviceApplication.getByUserAndDeveloper(userId, developerId);

    logger.info("Exit. views: {}.", views);
    return views;
  }

  /**
   * Gets device by definition.
   *
   * @param userId             the user id
   * @param deviceDefinitionId the device definition id
   * @param developerId        the developer id
   * @return the device by definition
   */
  @GetMapping(value = Router.DEVICE_CENTER_ROOT, params = {"userId", "deviceDefinitionId"})
  public DeviceView getDeviceByDefinition(@RequestParam String userId,
                                          @RequestParam String deviceDefinitionId, @RequestHeader
                                                String developerId) {
    logger.info("Enter. userId: {}, developerId: {}, deviceDefinitionId: {}.",
        userId, developerId, deviceDefinitionId);

    DeviceView device = deviceApplication
        .getByUserAndDefinition(userId, developerId, deviceDefinitionId);

    logger.info("Exit. device: {}.", device);

    return device;
  }

  // TODO: 17/7/13 查询参数， 分页，排序
  @GetMapping(value = Router.DEVICE_CENTER_ROOT)
  public List<DeviceData> getDeviceData(@RequestHeader String developerId) {
    logger.info("Enter. developerId: {}.", developerId);

    List<DeviceData> deviceData = deviceApplication.getDeviceData(developerId);

    logger.info("Exit. deviceData size: {}.", deviceData.size());

    return deviceData;
  }
}

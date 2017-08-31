package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.DeviceActivateResult;
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
 * Device controller.
 */
@RestController
@CrossOrigin
public class DeviceController {

  /**
   * Logger.
   */
  private final static Logger LOGGER = LoggerFactory.getLogger(DeviceController.class);

  /**
   * Device app.
   */
  @Autowired
  private transient DeviceApplication deviceApplication;

  /**
   * 激活设备。
   */
  @PostMapping(Router.DEVICE_CENTER_ROOT)
  public DeviceActivateResult activate(@RequestHeader String userId,
                                       @RequestBody @Valid DeviceDraft draft) {
    LOGGER.info("Enter. deviceDraft: {}.", draft);

    DeviceActivateResult result = deviceApplication.activate(draft, userId);

    LOGGER.info("Exit. result:{}.", result);
    return result;
  }

  /**
   * 解除用户与设备的绑定关系。
   */
  @DeleteMapping(Router.DEVICE_CENTER_WITH_ID)
  public void unbind(@RequestHeader String userId,
                     @PathVariable("id") String deviceId) {
    LOGGER.info("Enter. userId: {}, deviceId: {}.", userId, deviceId);

    deviceApplication.unbind(userId, deviceId);

    LOGGER.info("Exit.");
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
    LOGGER.info("Enter. deviceId: {}.", id);

    DeviceView view = deviceApplication.getByDeviceId(id, developerId, userId);

    LOGGER.info("Exit. deviceView: {}.", view);
    return view;
  }

  /**
   * 获取用户在某个开发者下的所有设备.
   *
   * @param userId      String
   * @param developerId String in header
   * @return list of device view
   */
  @GetMapping(value = Router.DEVICE_CENTER_ROOT, headers = {"userId", "developerId"})
  public List<DeviceView> getAllDeviceByUser(@RequestHeader String userId,
                                             @RequestHeader String developerId) {
    LOGGER.info("Enter. userId: {}, developerId: {}.", userId, developerId);

    List<DeviceView> views = deviceApplication.getByUserAndDeveloper(userId, developerId);

    LOGGER.info("Exit. views: {}.", views);
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
    LOGGER.info("Enter. userId: {}, developerId: {}, deviceDefinitionId: {}.",
      userId, developerId, deviceDefinitionId);

    DeviceView device = deviceApplication
      .getByUserAndDefinition(userId, developerId, deviceDefinitionId);

    LOGGER.info("Exit. device: {}.", device);

    return device;
  }

  /**
   * Count device.
   *
   * @return
   */
  @GetMapping(value = Router.DEVICE_COUNT)
  public Long countDevices() {
    LOGGER.info("Enter.");

    Long count = deviceApplication.countDevices();

    LOGGER.debug("Exit. device count: {}.", count);

    return count;
  }
}

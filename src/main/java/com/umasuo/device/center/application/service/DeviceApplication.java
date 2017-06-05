package com.umasuo.device.center.application.service;

import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.application.dto.mapper.DeviceBindingMapper;
import com.umasuo.device.center.application.dto.mapper.DeviceMapper;
import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.domain.model.DeviceBinding;
import com.umasuo.device.center.domain.service.DeviceBindingService;
import com.umasuo.device.center.domain.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by umasuo on 17/6/5.
 */
@Service
public class DeviceApplication {

  private final static Logger logger = LoggerFactory.getLogger(DeviceApplication.class);

  /**
   * device service.
   */
  private transient DeviceService deviceService;

  /**
   * Device binding service.
   */
  private transient DeviceBindingService deviceBindingService;

  /**
   * add new device to the system.
   *
   * @param draft draft
   * @return device view
   */
  @Transactional
  public DeviceView addDevice(DeviceDraft draft) {
    logger.debug("Enter. draft: {}.", draft);
    Device device = DeviceMapper.toModel(draft);
    Device savedDevice = deviceService.save(device);

    DeviceBinding deviceBinding = DeviceBindingMapper.toModel(draft);
    if (deviceBinding != null) {
      deviceBinding.setDeviceId(savedDevice.getId());
      deviceBindingService.save(deviceBinding);
    }

    DeviceView view = DeviceMapper.toView(savedDevice, deviceBinding);

    logger.debug("Exit. deviceView: {}.", view);
    return null;
  }

  /**
   * get one device by device id and user id.
   *
   * @param deviceId
   * @param userId
   * @return
   */
  public DeviceView getOneDeviceByUser(String deviceId, String userId) {
    logger.debug("Enter. deviceId: {}, userId: {}.", deviceId, userId);

    Device device = deviceService.get(deviceId);
    DeviceBinding deviceBinding = deviceBindingService.get(deviceId, userId);
    DeviceView view = DeviceMapper.toView(device, deviceBinding);

    logger.debug("Exit. deviceView: {}.", view);
    return view;
  }
}

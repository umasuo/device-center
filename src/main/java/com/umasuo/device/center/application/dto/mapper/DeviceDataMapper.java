package com.umasuo.device.center.application.dto.mapper;

import com.google.common.collect.Lists;
import com.umasuo.device.center.application.dto.DeviceData;
import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.infrastructure.enums.DeviceStatus;

import java.util.List;

/**
 * Created by Davis on 17/7/13.
 */
public final class DeviceDataMapper {

  /**
   * Instantiates a new Device data mapper.
   */
  private DeviceDataMapper() {
  }

  /**
   * To model list.
   *
   * @param devices the devices
   * @return the list
   */
  public static List<DeviceData> toModel(List<Device> devices) {
    List<DeviceData> models = Lists.newArrayList();

    if (devices != null) {
      devices.stream().forEach(device -> models.add(toModel(device)));
    }

    return models;
  }

  private static DeviceData toModel(Device device) {
    DeviceData model = new DeviceData();

    model.setDeviceId(device.getDeviceId());
    model.setUnionId(device.getUnionId());
    model.setStatus(device.getStatus());
    model.setActivateTime(device.getCreatedAt());

    if (DeviceStatus.BIND.equals(device.getStatus())) {
      model.setBindTime(device.getLastModifiedAt());
    } else {
      model.setUnbindTime(device.getLastModifiedAt());
    }

    // 直接在这里设置userId和productId，在后续拿到这两个直接匹配替换即可，不在需要device参与
    model.setUserPhone(device.getOwnerId());
    model.setProductName(device.getProductId());

    return model;
  }
}

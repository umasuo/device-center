package com.umasuo.device.center.application.dto.mapper;

import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.domain.model.DeviceBinding;

/**
 * Created by umasuo on 17/6/5.
 */
public class DeviceMapper {

  public static Device toModel(DeviceDraft draft) {
    Device device = new Device();
    device.setDeviceDefineId(draft.getDeviceDefineId());
    device.setCustomizedId(draft.getCustomizedId());
    device.setDeveloperId(draft.getDeveloperId());
    return device;
  }

  public static DeviceView toView(Device device, DeviceBinding deviceBinding) {
    DeviceView deviceView = new DeviceView();
    deviceView.setId(device.getId());
    deviceView.setCreatedAt(device.getCreatedAt());
    deviceView.setLastModifiedAt(device.getLastModifiedAt());
    deviceView.setVersion(device.getVersion());
    deviceView.setCustomizedId(device.getCustomizedId());
    deviceView.setDeviceDefineId(device.getDeviceDefineId());
    deviceView.setDeveloperId(device.getDeveloperId());

    if (deviceBinding != null) {
      deviceView.setUserId(deviceBinding.getUserId());
      deviceView.setBindTime(deviceBinding.getCreatedAt());
    }
    return deviceView;
  }
}

package com.umasuo.device.center.application.dto.mapper;

import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.domain.model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/6/5.
 */
public class DeviceMapper {

  public static Device toModel(DeviceDraft draft) {
    Device device = new Device();
    device.setDeviceDefineId(draft.getDeviceDefineId());
    device.setCustomizedId(draft.getCustomizedId());
    device.setDeveloperId(draft.getDeveloperId());
    device.setOwnerId(draft.getOwnerId());
    return device;
  }

  public static DeviceView toView(Device device) {
    DeviceView deviceView = new DeviceView();
    deviceView.setId(device.getId());
    deviceView.setCreatedAt(device.getCreatedAt());
    deviceView.setLastModifiedAt(device.getLastModifiedAt());
    deviceView.setVersion(device.getVersion());
    deviceView.setCustomizedId(device.getCustomizedId());
    deviceView.setDeviceDefineId(device.getDeviceDefineId());
    deviceView.setDeveloperId(device.getDeveloperId());

    return deviceView;
  }

  public static List<DeviceView> toView(List<Device> devices) {
    List<DeviceView> views = new ArrayList<>();
    devices.stream().forEach(
        device -> views.add(toView(device))
    );
    return views;
  }
}

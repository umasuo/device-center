package com.umasuo.device.center.application.dto.mapper;

import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.domain.model.Device;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by umasuo on 17/6/5.
 */
public class DeviceMapper {

  public static Device toModel(DeviceDraft draft, String developerId, String userId) {
    Device device = new Device();
    device.setProductId(draft.getProductId());
    device.setUnionId(draft.getUnionId());
    device.setDeveloperId(developerId);
    device.setOwnerId(userId);
    return device;
  }

  public static DeviceView toView(Device device) {
    DeviceView deviceView = new DeviceView();
    deviceView.setId(device.getId());
    deviceView.setCreatedAt(device.getCreatedAt());
    deviceView.setLastModifiedAt(device.getLastModifiedAt());
    deviceView.setVersion(device.getVersion());
    deviceView.setCustomizedId(device.getUnionId());
    deviceView.setDeviceDefineId(device.getProductId());
    deviceView.setOwnerId(device.getOwnerId());
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

  public static Device build(DeviceDraft draft, String userId, String developerId) {
    Device device = new Device();

    device.setDeveloperId(developerId);
    device.setOwnerId(userId);
    device.setUnionId(draft.getUnionId());
    device.setPublicKey(RandomStringUtils.randomAlphanumeric(9));
    device.setDeviceId(UUID.randomUUID().toString());

    return device;
  }
}

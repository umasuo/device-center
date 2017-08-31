package com.umasuo.device.center.application.dto.mapper;

import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.domain.model.Device;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Device mapper.
 */
public final class DeviceMapper {

  /**
   * Private default constructor.
   */
  private DeviceMapper() {
  }

  /**
   * From draft
   *
   * @param draft
   * @param userId
   * @param developerId
   * @return
   */
  public static Device toModel(DeviceDraft draft, String userId, String developerId) {
    Device device = new Device();

    device.setDeveloperId(developerId);
    device.setOwnerId(userId);
    device.setUnionId(draft.getUnionId());
    device.setProductId(draft.getProductId());
    device.setPublicKey(RandomStringUtils.randomAlphanumeric(9));

    return device;
  }

  /**
   * To view.
   *
   * @param device
   * @return
   */
  public static DeviceView toView(Device device) {
    DeviceView deviceView = new DeviceView();
    deviceView.setCreatedAt(device.getCreatedAt());
    deviceView.setLastModifiedAt(device.getLastModifiedAt());
    deviceView.setVersion(device.getVersion());
    deviceView.setUnionId(device.getUnionId());
    deviceView.setProductId(device.getProductId());
    deviceView.setDeveloperId(device.getDeveloperId());
    deviceView.setOwnerId(device.getOwnerId());
    deviceView.setPublicKey(device.getPublicKey());
    deviceView.setDeviceId(device.getDeviceId());
    deviceView.setStatus(device.getStatus());

    return deviceView;
  }

  /**
   * To view list.
   *
   * @param devices
   * @return
   */
  public static List<DeviceView> toView(List<Device> devices) {
    List<DeviceView> views = new ArrayList<>();
    devices.stream().forEach(
      device -> views.add(toView(device))
    );
    return views;
  }

}

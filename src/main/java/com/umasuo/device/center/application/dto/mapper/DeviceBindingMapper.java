package com.umasuo.device.center.application.dto.mapper;

import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.domain.model.DeviceBinding;

/**
 * Created by umasuo on 17/6/5.
 */
public class DeviceBindingMapper {

  public static DeviceBinding toModel(DeviceDraft draft) {
    DeviceBinding device = null;
    if (draft.getUserId() != null) {
      device = new DeviceBinding();
      device.setUserId(draft.getUserId());
    }
    return device;
  }

}

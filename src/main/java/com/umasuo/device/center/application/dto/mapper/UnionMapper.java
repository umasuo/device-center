package com.umasuo.device.center.application.dto.mapper;

import com.umasuo.device.center.application.dto.UnionDeviceView;
import com.umasuo.device.center.domain.model.UnionDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by umasuo on 17/7/4.
 */
public class UnionMapper {

  public static UnionDeviceView toView(UnionDevice entity) {
    UnionDeviceView view = new UnionDeviceView();

    view.setUnionId(entity.getUnionId());
    view.setSecretKey(entity.getSecretKey());

    return view;
  }

  public static List<UnionDeviceView> toView(List<UnionDevice> devices) {
    List<UnionDeviceView> views = new ArrayList<>();
    devices.stream().forEach(
        unionDevice -> views.add(toView(unionDevice))
    );
    return views;
  }
}

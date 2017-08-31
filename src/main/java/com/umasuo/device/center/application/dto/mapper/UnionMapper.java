package com.umasuo.device.center.application.dto.mapper;

import com.umasuo.device.center.application.dto.UnionDeviceView;
import com.umasuo.device.center.domain.model.UnionDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Union mapper.
 */
public final class UnionMapper {

  /**
   * Private default constructor.
   */
  private UnionMapper() {
  }

  /**
   * Model to view.
   *
   * @param entity
   * @return
   */
  public static UnionDeviceView toView(UnionDevice entity) {
    UnionDeviceView view = new UnionDeviceView();

    view.setUnionId(entity.getUnionId());
    view.setSecretKey(entity.getSecretKey());

    return view;
  }

  /**
   * Model list to view list.
   *
   * @param devices
   * @return
   */
  public static List<UnionDeviceView> toView(List<UnionDevice> devices) {
    List<UnionDeviceView> views = new ArrayList<>();
    devices.stream().forEach(
      unionDevice -> views.add(toView(unionDevice))
    );
    return views;
  }
}

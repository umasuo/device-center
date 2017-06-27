package com.umasuo.device.center.application.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Davis on 17/6/27.
 */
@Getter
@Setter
@ToString
public class UnionDeviceRequest {

  /**
   * Product id.
   */
  @NotNull
  private String productId;

  /**
   * Quantity.
   */
  @NotNull
  @Min(value = 1)
  @Max(value = 10000)
  private Integer quantity;
}

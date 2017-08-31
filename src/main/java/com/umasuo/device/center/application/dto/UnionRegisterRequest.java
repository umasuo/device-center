package com.umasuo.device.center.application.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Union register request.
 */
@Data
public class UnionRegisterRequest {

  /**
   * Union id.
   */
  @NotNull
  @Size(max = 32)
  private String unionId;

  /**
   * Product.
   */
  @NotNull
  @Size(min = 12, max = 32)
  private String productId;
}

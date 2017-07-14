package com.umasuo.device.center.application.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Davis on 17/7/14.
 */
@Data
public class UnionRegisterRequest {

  @NotNull
  @Size(max = 32)
  private String unionId;

  @NotNull
  @Size(min = 12, max = 32)
  private String productId;
}

package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Created by umasuo on 17/6/5.
 */
@Data
public class DeviceDraft implements Serializable {

  /**
   * auto generated serial id.
   */
  private static final long serialVersionUID = 8800732225747388907L;

  /**
   * device definition id.
   */
  @NotNull
  private String deviceDefineId;

  @NotNull
  private String unionId;

  @NotNull
  private String token;
}

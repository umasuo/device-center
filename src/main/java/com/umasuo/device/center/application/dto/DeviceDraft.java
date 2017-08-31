package com.umasuo.device.center.application.dto;

import lombok.Data;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * Device draft.
 */
@Data
public class DeviceDraft implements Serializable {

  /**
   * Auto generated serial id.
   */
  private static final long serialVersionUID = 8800732225747388907L;

  /**
   * device definition id.
   */
  @NotNull
  private String productId;

  /**
   * Device union id.
   */
  @NotNull
  private String unionId;

  /**
   * 暂时不需要token，先去掉。
   */
//  @NotNull
  private String token;
}

package com.umasuo.device.center.infrastructure;

/**
 * Created by umasuo on 17/6/3.
 */
public class Router {

  public static final String VERSION = "/v1";

  /**
   * device root.
   */
  public static final String DEVICE_CENTER_ROOT = VERSION + "/devices";

  /**
   * The constant REPORT_ROOT.
   */
  public static final String REPORT_ROOT = DEVICE_CENTER_ROOT + "/reports";

  /**
   * device with id.
   */
  public static final String DEVICE_CENTER_WITH_ID = DEVICE_CENTER_ROOT + "/{id}";

  /**
   * Device token root.
   */
  public static final String DEVICE_TOKEN = DEVICE_CENTER_ROOT + "/tokens";

  public static final String UNION_ROOT = DEVICE_CENTER_ROOT + "/unions";
}

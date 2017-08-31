package com.umasuo.device.center.infrastructure;

/**
 * Service router.
 */
public class Router {

  /**
   * Version.
   */
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

  /**
   * Union root.
   */
  public static final String UNION_ROOT = DEVICE_CENTER_ROOT + "/unions";

  /**
   * Device message root.
   */
  public static final String DEVICE_MESSAGE = DEVICE_CENTER_WITH_ID + "/messages";

  /**
   * Device Data.
   */
  public static final String DEVICE_DATA = DEVICE_CENTER_ROOT + "/data";

  /**
   * Union register.
   */
  public static final String UNION_ROOT_REGISTER = UNION_ROOT + "/register";

  /**
   * Device count.
   */
  public static final String DEVICE_COUNT = "/v1/admin/devices/count";
}

package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.DeviceReportView;
import com.umasuo.device.center.application.service.ReportApplication;
import com.umasuo.device.center.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Report controller for fetching report data.
 */
@RestController
public class ReportController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

  /**
   * Report app.
   */
  @Autowired
  private transient ReportApplication reportApplication;

  /**
   * Gets report by time.
   *
   * @param startTime the start time
   * @param endTime   the end time
   * @return the report by time
   */
  @GetMapping(value = Router.REPORT_ROOT, params = {"startTime", "endTime"})
  public @ResponseBody
  List<DeviceReportView> getPeriodReport(@RequestParam long startTime,
                                         @RequestParam long endTime) {
    LOGGER.info("Enter. startTime: {}, endTime: {}.", startTime, endTime);

    List<DeviceReportView> reports = reportApplication.getPeriodReport(startTime, endTime);

    LOGGER.info("Exit. report size: {}.", reports.size());

    return reports;
  }

  /**
   * Gets report by time.
   *
   * @param developerId the developer id
   * @param startTime   the start time
   * @return the report by time
   */
  @GetMapping(value = Router.REPORT_ROOT, params = {"startTime"})
  public @ResponseBody
  List<DeviceReportView> getDeveloperReport(@RequestHeader String developerId,
                                            @RequestParam long startTime) {
    LOGGER.info("Enter. developerId: {}, startTime: {}.", developerId, startTime);

    List<DeviceReportView> reports = reportApplication
      .getDeveloperReportByTime(startTime, developerId);

    LOGGER.info("Exit. report size: {}.", reports.size());

    return reports;
  }
}

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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Davis on 17/6/16.
 */
@RestController
public class ReportController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ReportController.class);

  @Autowired
  private transient ReportApplication reportApplication;

  /**
   * Gets report by time.
   *
   * @param startTime the start time
   * @param endTime the end time
   * @return the report by time
   */
  @GetMapping(value = Router.REPORT_ROOT, params = {"startTime", "endTime"})
  public List<DeviceReportView> getPeriodReport(@RequestParam("startTime") long startTime,
      @RequestParam("endTime") long endTime) {
    LOG.info("Enter. startTime: {}, endTime: {}.", startTime, endTime);

    List<DeviceReportView> reports = reportApplication.getPeriodReport(startTime, endTime);

    LOG.info("Exit. report size: {}.", reports.size());

    return reports;
  }

  /**
   * Gets report by time.
   *
   * @param developerId the developer id
   * @param startTime the start time
   * @return the report by time
   */
  @GetMapping(value = Router.REPORT_ROOT, params = {"startTime"})
  public List<DeviceReportView> getDeveloperReport(@RequestHeader String developerId,
      @RequestParam("startTime") long startTime) {
    LOG.info("Enter. developerId: {}, startTime: {}.", developerId, startTime);

    List<DeviceReportView> reports = reportApplication
        .getDeveloperReportByTime(startTime, developerId);

    LOG.info("Exit. report size: {}.", reports.size());

    return reports;
  }
}

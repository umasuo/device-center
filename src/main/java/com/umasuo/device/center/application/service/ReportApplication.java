package com.umasuo.device.center.application.service;

import com.umasuo.device.center.application.dto.DeviceReportView;
import com.umasuo.device.center.domain.service.DeviceService;
import com.umasuo.device.center.infrastructure.validator.TimeValidator;
import com.umasuo.device.center.infrastructure.util.ReportUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Davis on 17/6/16.
 */
@Service
public class ReportApplication {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ReportApplication.class);

  /**
   * device service.
   */
  @Autowired
  private transient DeviceService deviceService;

  /**
   * redis ops.
   */
  @Autowired
  private transient RedisTemplate redisTemplate;

  /**
   * Gets report by time.
   *
   * @param startTime the start time
   * @param endTime   the end time
   * @return the report by time
   */
  public List<DeviceReportView> getPeriodReport(long startTime, long endTime) {
    LOG.debug("Enter. startTime: {}, endTime: {}.", startTime, endTime);

    TimeValidator.validate(startTime, endTime);

    //获取激活设备总量报表数据
    List<HashMap> totalReport = deviceService.getTotalReport();

    List<HashMap> increaseReport = deviceService.getIncreaseReport(startTime, endTime);

    List<DeviceReportView> result = ReportUtils.mergeReport(totalReport, increaseReport);

    getOnlineCount(result);

    LOG.debug("Exit. report size: {}.", result.size());

    return result;
  }

  /**
   * Gets report by time.
   *
   * @param startTime   the start time
   * @param developerId the developer id
   * @return the report by time
   */
  public List<DeviceReportView> getDeveloperReportByTime(long startTime, String developerId) {
    LOG.debug("Enter. startTime: {}, developerId: {}.", startTime, developerId);

    TimeValidator.validate(startTime);

    List<HashMap> totalReport = deviceService.getTotalReport(developerId);

    List<HashMap> increaseReport = deviceService.getIncreaseReport(developerId, startTime);

    List<DeviceReportView> result = ReportUtils.mergeReport(totalReport, increaseReport);

    getOnlineCount(result);

    LOG.debug("Exit. report size: {}.", result.size());

    return result;
  }

  /**
   * 统计在线的设备用户数.
   *
   * @param report
   */
  public void getOnlineCount(List<DeviceReportView> report) {
    report.stream().forEach(
        reportView -> {
          String key = SessionApplication.DEVICE_KEY + ":" + reportView.getDeveloperId() + "*";
          reportView.setOnlineNumber(redisTemplate.keys(key).size());
        }
    );
  }
}

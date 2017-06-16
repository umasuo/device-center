package com.umasuo.device.center.infrastructure.util;

import com.google.common.collect.Lists;
import com.umasuo.device.center.application.dto.DeviceReportView;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Davis on 17/6/16.
 */
public final class ReportUtils {

  /**
   * Instantiates a new Report utils.
   */
  private ReportUtils() {
  }

  /**
   * Merge report.
   *
   * @param totalReports the total reports
   * @param registerReports the register reports
   * @return the list
   */
  public static List<DeviceReportView> mergeReport(List<HashMap> totalReports,
      List<HashMap> registerReports) {
    List<DeviceReportView> result = Lists.newArrayList();

    Consumer<HashMap> totalConsumer = map -> handleTotalReport(result, map);

    totalReports.forEach(totalConsumer);

    Consumer<HashMap> registerConsumer = map -> handleRegisterReport(result, map);

    registerReports.forEach(registerConsumer);

    return result;
  }

  private static void handleRegisterReport(List<DeviceReportView> result, HashMap map) {
    Consumer<DeviceReportView> consumer = deviceReportView -> {
      if (deviceReportView.getDeveloperId().equals(map.get("developerId").toString()) &&
          deviceReportView.getDeviceDefinitionId().equals(map.get("definitionId").toString())) {
        deviceReportView.setRegisterNumber(Integer.valueOf(map.get("registerCount").toString()));
      }
    };

    result.stream().forEach(consumer);
  }

  private static void handleTotalReport(List<DeviceReportView> result, HashMap map) {
    DeviceReportView reportView = new DeviceReportView();

    reportView.setDeveloperId(map.get("developerId").toString());
    reportView.setDeviceDefinitionId(map.get("definitionId").toString());
    reportView.setTotalNumber(Integer.valueOf(map.get("totalCount").toString()));

    // TODO: 17/6/16 
    reportView.setOnlineNumber(0);

    result.add(reportView);
  }
}

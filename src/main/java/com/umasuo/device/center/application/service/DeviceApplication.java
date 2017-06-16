package com.umasuo.device.center.application.service;

import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceReportView;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.application.dto.mapper.DeviceMapper;
import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.domain.service.DeviceService;
import com.umasuo.device.center.infrastructure.TimeValidator;
import com.umasuo.device.center.infrastructure.util.ReportUtils;
import com.umasuo.exception.NotExistException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by umasuo on 17/6/5.
 */
@Service
public class DeviceApplication {

  private final static Logger logger = LoggerFactory.getLogger(DeviceApplication.class);

  /**
   * device service.
   */
  @Autowired
  private transient DeviceService deviceService;

  /**
   * add new device to the system.
   *
   * @param draft draft
   * @return device view
   */
  @Transactional
  public DeviceView addDevice(DeviceDraft draft) {
    logger.debug("Enter. draft: {}.", draft);
    Device device = DeviceMapper.toModel(draft);
    Device savedDevice = deviceService.save(device);

    DeviceView view = DeviceMapper.toView(savedDevice);

    logger.debug("Exit. deviceView: {}.", view);
    return view;
  }

  /**
   * get one device by device id.
   *
   * @param deviceId String
   * @return DeviceView by device id
   */
  public DeviceView getByDeviceId(String deviceId) {
    logger.debug("Enter. deviceId: {}.", deviceId);

    Device device = deviceService.get(deviceId);
    DeviceView view = DeviceMapper.toView(device);

    logger.debug("Exit. deviceView: {}.", view);
    return view;
  }

  /**
   * 获取一个用户在某个开发者下的所有设备。
   *
   * @param userId the user id
   * @param developerId the developer id
   * @return 设备列表 by user and developer
   */
  public List<DeviceView> getByUserAndDeveloper(String userId, String developerId) {
    logger.debug("Enter. userId: {}, developerId: {}.", userId, developerId);

    List<Device> devices = deviceService.getByUserAndDeveloper(userId, developerId);
    List<DeviceView> views = DeviceMapper.toView(devices);

    logger.debug("Exit. viewSize: {}.", views.size());
    logger.trace("DeviceView: {}.", views);
    return views;
  }

  /**
   * 获取某种设备的统计数量
   *
   * @param deviceDefinitionId String
   * @return long device count
   */
  public long getDeviceCount(String deviceDefinitionId) {
    logger.debug("Enter. deviceDefinitionId: {}.", deviceDefinitionId);

    long count = deviceService.countByDeviceDefine(deviceDefinitionId);

    logger.debug("Exit. count: {}.", count);
    return count;
  }

  /**
   * Gets by user and definition.
   *
   * @param userId the user id
   * @param developerId the developer id
   * @param deviceDefinitionId the device definition id
   * @return the by user and definition
   */
  public DeviceView getByUserAndDefinition(String userId, String developerId,
      String deviceDefinitionId) {
    logger.debug("Enter. userId: {}, developerId: {}, deviceDefinitionId: {}.", userId, developerId,
        deviceDefinitionId);

    Device device = deviceService.getByUserAndDefinition(userId, developerId, deviceDefinitionId);
    if (device == null) {
      logger.debug("Can not find device by user: {}, developer: {}, deviceDefinition: {}.",
          userId, developerId, deviceDefinitionId);
      throw new NotExistException("Device not find");
    }
    DeviceView result = DeviceMapper.toView(device);

    logger.debug("Exit. device: {}.", result);

    return result;
  }


  /**
   * Gets report by time.
   *
   * @param startTime the start time
   * @param endTime the end time
   * @return the report by time
   */
  public List<DeviceReportView> getPeriodReport(long startTime, long endTime) {
    logger.debug("Enter. startTime: {}, endTime: {}.", startTime, endTime);

    TimeValidator.validate(startTime, endTime);

    List<HashMap> totalReport = deviceService.getAllReport();

    List<HashMap> registerReport = deviceService.getRegisteredReport(startTime, endTime);

    List<DeviceReportView> result = ReportUtils.mergeReport(totalReport, registerReport);

    return result;
  }

  /**
   * Gets report by time.
   *
   * @param startTime the start time
   * @param developerId the developer id
   * @return the report by time
   */
  public List<DeviceReportView> getDeveloperReportByTime(long startTime, String developerId) {
    logger.debug("Enter. startTime: {}, developerId: {}.", startTime, developerId);

    TimeValidator.validate(startTime);

    List<HashMap> totalReport = deviceService.getDeveloperAllReport(developerId);

    List<HashMap> registerReport = deviceService.getDeveloperRegisteredReport(developerId, startTime);

    List<DeviceReportView> result = ReportUtils.mergeReport(totalReport, registerReport);

    return result;
  }
}
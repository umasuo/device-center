package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.DeviceDraft;
import com.umasuo.device.center.application.dto.DeviceView;
import com.umasuo.device.center.application.service.DeviceApplication;
import com.umasuo.device.center.infrastructure.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by umasuo on 17/6/5.
 */
@RestController
@CrossOrigin
public class DeviceController {

  private final static Logger logger = LoggerFactory.getLogger(DeviceController.class);

  @Autowired
  private transient DeviceApplication deviceApplication;


  @PostMapping(Router.DEVICE_CENTER_ROOT)
  public DeviceView addDevice(@RequestBody @Valid DeviceDraft draft) {
    logger.info("Enter. deviceDraft: {}.", draft);

    DeviceView view = deviceApplication.addDevice(draft);

    logger.info("Exit. deviceView: {}.", view);
    return view;
  }

}

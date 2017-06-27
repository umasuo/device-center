package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.UnionDeviceRequest;
import com.umasuo.device.center.domain.model.UnionDevice;
import com.umasuo.device.center.domain.service.UnionDeviceService;
import com.umasuo.device.center.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by Davis on 17/6/27.
 */
@RestController
public class UnionDeviceController {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(UnionDeviceController.class);

  /**
   * The application.
   */
  @Autowired
  private UnionDeviceService unionDeviceService;

  @PostMapping(Router.UNION_ROOT)
  public List<UnionDevice> batchCreate(@RequestHeader("developerId") String developerId,
      @RequestBody @Valid UnionDeviceRequest request) {
    LOG.info("Enter. developerId: {}, request: {}.", developerId, request);

    List<UnionDevice> result = unionDeviceService.batchCreate(developerId, request);

    LOG.info("Exit.");
    return result;
  }
}

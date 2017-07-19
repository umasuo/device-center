package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.UnionDeviceRequest;
import com.umasuo.device.center.application.dto.UnionDeviceView;
import com.umasuo.device.center.application.dto.UnionRegisterRequest;
import com.umasuo.device.center.application.dto.mapper.UnionMapper;
import com.umasuo.device.center.domain.model.UnionDevice;
import com.umasuo.device.center.domain.service.UnionDeviceService;
import com.umasuo.device.center.infrastructure.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

/**
 * Created by Davis on 17/6/27.
 */
@CrossOrigin
@RestController
public class UnionDeviceController {

  /**
   * Logger.
   */
  private static final Logger logger = LoggerFactory.getLogger(UnionDeviceController.class);

  /**
   * The application.
   */
  @Autowired
  private UnionDeviceService unionDeviceService;

  @PostMapping(Router.UNION_ROOT)
  public List<UnionDeviceView> batchCreate(@RequestHeader("developerId") String developerId,
                                           @RequestBody @Valid UnionDeviceRequest request) {
    logger.info("Enter. developerId: {}, request: {}.", developerId, request);

    List<UnionDevice> unions = unionDeviceService.batchCreate(developerId, request);

    logger.info("Exit. unionSize: {}.", unions.size());
    return UnionMapper.toView(unions);
  }

  @PostMapping(Router.UNION_ROOT_REGISTER)
  public UnionDeviceView register(@RequestHeader("developerId") String developerId,
      @RequestBody @Valid UnionRegisterRequest request) {
    logger.info("Enter. developerId: {}, request: {}.", developerId, request);

    UnionDeviceView unionDeviceView = unionDeviceService.register(developerId, request);

    logger.debug("Exit. unionDevice: {}.", unionDeviceView);

    return unionDeviceView;
  }
}

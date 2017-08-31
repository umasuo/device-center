package com.umasuo.device.center.application.rest;

import com.umasuo.device.center.application.dto.UnionDeviceRequest;
import com.umasuo.device.center.application.dto.UnionDeviceView;
import com.umasuo.device.center.application.dto.UnionRegisterRequest;
import com.umasuo.device.center.application.service.UnionApplication;
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
 * Union device controller.
 */
@CrossOrigin
@RestController
public class UnionDeviceController {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(UnionDeviceController.class);

  /**
   * The application.
   */
  @Autowired
  private transient UnionApplication unionApplication;

  /**
   * Batch create union device.
   *
   * @param developerId
   * @param request
   * @return
   */
  @PostMapping(Router.UNION_ROOT)
  public List<UnionDeviceView> batchCreate(@RequestHeader("developerId") String developerId,
                                           @RequestBody @Valid UnionDeviceRequest request) {
    LOGGER.info("Enter. developerId: {}, request: {}.", developerId, request);

    List<UnionDeviceView> unions = unionApplication.batchCreate(developerId, request);

    LOGGER.info("Exit. unionSize: {}.", unions.size());
    return unions;
  }

  /**
   * Register.
   * @param developerId
   * @param request
   * @return
   */
  @PostMapping(Router.UNION_ROOT_REGISTER)
  public UnionDeviceView register(@RequestHeader("developerId") String developerId,
                                  @RequestBody @Valid UnionRegisterRequest request) {
    LOGGER.info("Enter. developerId: {}, request: {}.", developerId, request);

    UnionDeviceView unionDeviceView = unionApplication.register(developerId, request);

    LOGGER.debug("Exit. unionDevice: {}.", unionDeviceView);

    return unionDeviceView;
  }


}

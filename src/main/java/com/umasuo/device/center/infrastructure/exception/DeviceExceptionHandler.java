package com.umasuo.device.center.infrastructure.exception;

import com.umasuo.exception.handler.ExceptionHandler;
import com.umasuo.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Device center's exception handler.
 */
@Component
public class DeviceExceptionHandler implements ExceptionHandler,
  HandlerExceptionResolver {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(DeviceExceptionHandler.class);

  /**
   * Resolve exception.
   *
   * @param request
   * @param response
   * @param handler
   * @param ex
   * @return
   */
  @Override
  public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                       Object handler, Exception ex) {
    setResponse(request, response, handler, ex);
    addExceptionBody(response, ex);
    return new ModelAndView();
  }

  /**
   * add customized message body to the response.
   */
  private void addExceptionBody(HttpServletResponse response, Exception ex) {
    try {
      ExceptionBody body = getBody(ex);
      if (body != null) {
        response.getWriter().print(JsonUtils.serialize(body));
      }
    } catch (IOException e) {
      LOGGER.error("failed to write response JSON", e);
      throw new IllegalStateException(e);
    }
  }

  /**
   * get customized message body by exception type.
   *
   * @param ex exception.
   * @return exception body.
   */
  private ExceptionBody getBody(Exception ex) {
    ExceptionBody body = null;
    if (ex instanceof AlreadyBoundException) {
      body = ExceptionBody.of(ExceptionBody.DEVICE_ALREADY_BOUND_CODE,
        ExceptionBody.DEVICE_ALREADY_BOUND_MESSAGE);
    }
    return body;
  }
}
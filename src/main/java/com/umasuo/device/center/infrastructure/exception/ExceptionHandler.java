package com.umasuo.device.center.infrastructure.exception;

import com.umasuo.device.center.infrastructure.util.JsonUtils;
import com.umasuo.exception.AlreadyExistException;
import com.umasuo.exception.NotExistException;
import com.umasuo.exception.PasswordErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Davis on 17/6/19.
 */
@Component
public class ExceptionHandler implements com.umasuo.exception.handler.ExceptionHandler,
    HandlerExceptionResolver {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

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
      LOG.error("failed to write response JSON", e);
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
    if (ex instanceof NotExistException) {
      body = ExceptionBody.of(ExceptionBody.DEVELOPER_NOT_EXIST_CODE, ExceptionBody
          .DEVELOPER_NOT_EXIST_MESSAGE);
    }
    if (ex instanceof AlreadyExistException) {
      body = ExceptionBody.of(ExceptionBody.DEVELOPER_ALREADY_EXIST_CODE, ExceptionBody
          .DEVELOPER_ALREADY_EXIST_MESSAGE);
    }
    if (ex instanceof PasswordErrorException) {
      body = ExceptionBody.of(ExceptionBody.EMAIL_OR_PASSWORD_ERROR_CODE, ExceptionBody
          .EMAIL_OR_PASSWORD_ERROR_MESSAGE);
    }
    return body;
  }
}
package com.umasuo.device.center.infrastructure.validator;

import com.umasuo.exception.ParametersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Time validator.
 */
public final class TimeValidator {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(TimeValidator.class);

  /**
   * Instantiates a new Time validator.
   */
  private TimeValidator() {
  }

  /**
   * Validate.
   *
   * @param startTime the start time
   * @param endTime the end time
   */
  public static void validate(long startTime, long endTime) {

    ZonedDateTime start = Instant.ofEpochMilli(startTime).atZone(ZoneOffset.UTC);
    ZonedDateTime end = Instant.ofEpochMilli(endTime).atZone(ZoneOffset.UTC);

    long hoursRange = ChronoUnit.HOURS.between(start, end);

    if (hoursRange > 25 || hoursRange < -25) {
      LOGGER.debug("Can not get report more than 24 hours. startDate: {}, endDate: {}.", start, end);
      throw new ParametersException("Can not get report more than 24 hours");
    }

    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

    long daysRange = ChronoUnit.DAYS.between(now, end);
    if (daysRange > 1 || daysRange < -1) {
      LOGGER.debug("Can not get more than 1 day ago report");
      throw new ParametersException("Can not get more than 1 day ago report");
    }
  }

  /**
   * Validate start time.
   * @param startTime
   */
  public static void validate(long startTime) {
    ZonedDateTime start = Instant.ofEpochMilli(startTime).atZone(ZoneOffset.UTC);

    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);

    long daysRange = ChronoUnit.DAYS.between(now, start);
    if (daysRange > 1 || daysRange < -1) {
      LOGGER.debug("Can not get more than 1 day ago report");
      throw new ParametersException("Can not get more than 1 day ago report");
    }
  }
}

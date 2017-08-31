package com.umasuo.device.center.application.service;

import com.umasuo.device.center.application.dto.ProductView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.springframework.http.HttpMethod.GET;

/**
 * Rest client.
 */
@Service
public class RestClient {

  /**
   * Logger.
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

  /**
   * Product Url.
   */
  @Value("${product.service.url:http://localhost:8805/}")
  private transient String productUrl;

  /**
   * Rest client.
   */
  private transient RestTemplate restTemplate = new RestTemplate();

  /**
   * Get product.
   *
   * @param developerId
   * @param productId
   * @return
   */
  public ProductView getProduct(String developerId, String productId) {
    LOGGER.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);

    HttpEntity entity = new HttpEntity(headers);

    String url = productUrl + "v1/products/" + productId;

    ResponseEntity<ProductView> response =
      restTemplate.exchange(url, GET, entity, ProductView.class);

    LOGGER.debug("Exit.");

    return response.getBody();
  }
}

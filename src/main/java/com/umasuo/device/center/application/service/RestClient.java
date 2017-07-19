package com.umasuo.device.center.application.service;

import static org.springframework.http.HttpMethod.GET;

import com.umasuo.device.center.application.dto.ProductView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Davis on 17/7/19.
 */
@Service
public class RestClient {

  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);

  @Value("${product.service.url:http://localhost:8805/}")
  private String productUrl;

  private transient RestTemplate restTemplate = new RestTemplate();

  public ProductView getProduct(String developerId, String productId) {
    LOG.debug("Enter. developerId: {}, productId: {}.", developerId, productId);

    HttpHeaders headers = new HttpHeaders();
    headers.set("developerId", developerId);

    HttpEntity entity = new HttpEntity(headers);

    String url = productUrl + "v1/products/" + productId;

    ResponseEntity<ProductView> response =
        restTemplate.exchange(url, GET, entity, ProductView.class);

    LOG.debug("Exit.");

    return response.getBody();
  }
}

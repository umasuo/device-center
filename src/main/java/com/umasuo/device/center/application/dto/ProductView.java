package com.umasuo.device.center.application.dto;

import com.umasuo.device.center.infrastructure.enums.ProductStatus;
import lombok.Data;

/**
 * Created by Davis on 17/7/19.
 */
@Data
public class ProductView {

  private String id;

  /**
   * Which developer this kind of product belong to.
   */
  private String developerId;

  /**
   * Product status: published, unpublished.
   */
  private ProductStatus status;

  /**
   * name of the Product.
   */
  private String name;

  private String productTypeId;

  /**
   * Product icon.
   */
  private String icon;
}
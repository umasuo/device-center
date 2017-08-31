package com.umasuo.device.center.application.dto;

import com.umasuo.device.center.infrastructure.enums.ProductStatus;
import lombok.Data;

/**
 * Product view.
 */
@Data
public class ProductView {

  /**
   * Product id.
   */
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

  /**
   * Product type id.
   */
  private String productTypeId;

  /**
   * Product icon.
   */
  private String icon;
}
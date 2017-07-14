package com.umasuo.device.center.domain.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Created by Davis on 17/6/27.
 */
@Entity
@Table(name = "union_device")
@Data
@EntityListeners(AuditingEntityListener.class)
public class UnionDevice {

  @Id
  @Column(name = "union_id")
  private String unionId;

  /**
   * The Created at.
   */
  @CreatedDate
  @Column(name = "created_at")
  protected Long createdAt;

  /**
   * The developer id.
   */
  @Column(name = "developer_id")
  private String developerId;

  @Column(name = "product_Id")
  private String productId;

  /**
   * 设备出产时带有的一个用于加密数据的key.
   */
  private String secretKey;
}

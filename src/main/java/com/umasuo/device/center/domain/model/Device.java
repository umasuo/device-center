package com.umasuo.device.center.domain.model;

import com.umasuo.device.center.infrastructure.enums.DeviceStatus;

import lombok.Data;
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
 * Created by umasuo on 17/6/3.
 */
@Entity
@Table(name = "device")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Device {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(name = "id")
  private String id;

  /**
   * The Created at.
   */
  @CreatedDate
  @Column(name = "created_at")
  protected Long createdAt;

  /**
   * The Last modified at.
   */
  @LastModifiedDate
  @Column(name = "last_modified_at")
  protected Long lastModifiedAt;

  /**
   * version used for update date check.
   */
  @Version
  private Integer version;

  /**
   * 从系统批量获取的ID，用于唯一标志该设备。
   */
  private String unionId;

  /**
   * 任何接入云平台的设备，都属于一个事先定义好的设备类型.
   */
  private String productId;

  /**
   * 开发者ID，任何接入云平台的设备，都属于一个固定的开发者.
   */
  private String developerId;

  /**
   * 设备拥有者的ID，这里是userId，而不是developerID，如果没有指定，那么这台设备的拥有者则属于开发者本身.
   */
  private String ownerId;

  private String publicKey;

  private String deviceId;

  private DeviceStatus status;
}

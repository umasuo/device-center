package com.umasuo.device.center.infrastructure.repository;

import com.umasuo.device.center.domain.model.UnionDevice;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Davis on 17/6/27.
 */
public interface UnionDeviceRepository extends JpaRepository<UnionDevice, String>{

}

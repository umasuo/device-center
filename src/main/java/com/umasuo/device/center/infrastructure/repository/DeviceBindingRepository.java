package com.umasuo.device.center.infrastructure.repository;

import com.umasuo.device.center.domain.model.Device;
import com.umasuo.device.center.domain.model.DeviceBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface DeviceBindingRepository extends JpaRepository<DeviceBinding, String> {

}

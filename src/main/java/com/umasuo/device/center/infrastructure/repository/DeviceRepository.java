package com.umasuo.device.center.infrastructure.repository;

import com.umasuo.device.center.domain.model.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Created by umasuo on 17/2/10.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

  @Query("select new map(d.developerId as developerId, d.deviceDefineId as definitionId, count(d) as totalCount) from Device d group by d.developerId, d.deviceDefineId")
  List<HashMap> getDeviceReport();

  @Query("select new map(d.developerId as developerId, d.deviceDefineId as definitionId, count(d) as registerCount) from Device d where d.createdAt >= ?1 and d.createdAt < ?2 group by d.developerId, d.deviceDefineId")
  List<HashMap> getRegisterDeviceReport(long startTime, long endTime);
}

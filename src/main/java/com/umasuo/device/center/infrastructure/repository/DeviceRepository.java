package com.umasuo.device.center.infrastructure.repository;

import com.umasuo.device.center.domain.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * Device repository.
 */
@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {

  /**
   * Get report.
   *
   * @return
   */
  @Query("select new map(d.developerId as developerId, d.productId as productId, count(d) as totalCount) from Device d group by d.developerId, d.productId")
  List<HashMap> getReport();

  /**
   * Get report with developer id.
   *
   * @param developerId
   * @return
   */
  @Query("select new map(d.developerId as developerId, d.productId as productId, count(d) as totalCount) from Device d group by d.developerId, d.productId having d.developerId = ?1")
  List<HashMap> getReport(String developerId);

  /**
   * Get report data by time.
   *
   * @param startTime
   * @param endTime
   * @return
   */
  @Query("select new map(d.developerId as developerId, d.productId as productId, count(d) as registerCount) from Device d where d.createdAt >= ?1 and d.createdAt < ?2 group by d.developerId, d" +
    ".productId")
  List<HashMap> getIncreaseReport(long startTime, long endTime);

  /**
   * Get developer register report data.
   *
   * @param developerId
   * @param startTime
   * @return
   */
  @Query("select new map(d.developerId as developerId, d.productId as productId, count(d) as registerCount) from Device d where d.createdAt >= ?2 group by d.developerId, d.productId having d" +
    ".developerId = ?1")
  List<HashMap> getDeveloperRegisterReport(String developerId, long startTime);
}

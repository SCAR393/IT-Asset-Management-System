package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.domain.Asset;

@Repository
public interface AssetRepo extends JpaRepository<Asset, Long> {
	
	List<Asset> findByEmployee_EmployeeId(Long employeeId);
	
long countByStatus(String status);
	
	
	List<Asset> findByStatus(String status);
	List<Asset> findByLot_Id(Long lotId);
	List<Asset> findTop10ByOrderByAssetIdDesc();
	List<Asset> findByLocation_LocationId(Long locationId);
	List<Asset> findTop10ByLocation_LocationIdOrderByAssetIdDesc(Long locationId);
//	@Query("SELECT COUNT(a) FROM Asset a WHERE a.product.productId = :productId AND a.location.locationId = :locationId")
//    long countByProductIdAndLocationId(@Param("productId") Long productId, @Param("locationId") Long locationId);

	
//	 @Query("SELECT a, e FROM Asset a JOIN Employee e ON a.employeeId = e.employeeId WHERE a.location.locationId = :locationId")
//	 List<Object[]> findAssetsWithEmployeesByLocation(Long locationId);
}

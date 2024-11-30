package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff,String> {

   Staff findByStaffId(String staffId);

   Boolean existsByStaffId(String id);
   void deleteByStaffId(String id);
   @Query(value = "SELECT staff_id FROM staff ORDER BY staff_id DESC LIMIT 1", nativeQuery = true)
   String findLatestStaffId();
}

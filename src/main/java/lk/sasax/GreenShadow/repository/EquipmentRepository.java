package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EquipmentRepository extends JpaRepository<Equipment,String> {

    @Query(value = "SELECT equipment_id FROM equipment ORDER BY equipment_id DESC LIMIT 1", nativeQuery = true)
    String findLatestEquipmentId();
}

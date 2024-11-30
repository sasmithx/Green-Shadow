package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.Staff;
import lk.sasax.GreenShadow.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VechicleRepository extends JpaRepository<Vehicle,String> {

    Staff findByVehicleCode(String staffId);

    Boolean existsByVehicleCode(String id);
    void deleteByVehicleCode(String id);
    @Query(value = "SELECT vehicle_code FROM vehicle ORDER BY vehicle_code DESC LIMIT 1", nativeQuery = true)
    String findLatestVehicleCode();

}

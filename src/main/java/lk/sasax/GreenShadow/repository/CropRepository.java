package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CropRepository extends JpaRepository<Crop,String> {

    Crop findByCropCode(String id);

    Optional<Crop> findFirstByOrderByCropCodeDesc();
    @Query(value = "SELECT c.crop_code FROM crops c ORDER BY c.crop_code DESC",nativeQuery = true)
    String findLatestCropCode();
}

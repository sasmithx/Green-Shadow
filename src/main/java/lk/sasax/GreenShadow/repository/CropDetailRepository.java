package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.CropDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CropDetailRepository extends JpaRepository<CropDetails,String> {

    // Custom method to find crop details by crop code
    @Query("SELECT cd FROM CropDetails cd WHERE cd.crop_code = :cropCode")
    Optional<CropDetails> findByCropCode(@Param("cropCode") String cropCode);
}

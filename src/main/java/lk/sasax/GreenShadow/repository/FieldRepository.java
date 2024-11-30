package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.Field;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field,String> {
    @Query("SELECT f.fieldCode FROM Field f ORDER BY f.fieldCode DESC")
    List<String> findLatestFieldCode(Pageable pageable);

    Optional<Field> findByFieldCode(String fieldCode);

}

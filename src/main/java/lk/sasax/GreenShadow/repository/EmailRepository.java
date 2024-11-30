package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email,Long> {
}

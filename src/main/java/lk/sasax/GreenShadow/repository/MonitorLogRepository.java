package lk.sasax.GreenShadow.repository;

import lk.sasax.GreenShadow.entity.MonitoringLogService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitorLogRepository extends JpaRepository<MonitoringLogService,String> {

    Optional<MonitoringLogService> findByLogCode(String logCode);

}

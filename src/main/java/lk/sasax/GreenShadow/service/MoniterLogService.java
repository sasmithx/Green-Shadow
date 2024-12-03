package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.dto.FieldDTO;
import lk.sasax.GreenShadow.dto.MonitorlogDTO;

import java.util.List;

public interface MoniterLogService {
    void saveLog(MonitorlogDTO monitorlogDTO);
    void updateLog(String id, MonitorlogDTO monitorlogDTO, FieldDTO fieldDTO);
    List<MonitorlogDTO> getAllMonitoringLogs();
    String nextCode(String prefix);
}

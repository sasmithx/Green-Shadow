package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.CropDetailDTO;
import lk.sasax.GreenShadow.dto.MonitorlogDTO;
import lk.sasax.GreenShadow.service.impl.MonitorlogServiceIMPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/log")
public class MonitorLogController {

    @Autowired
    MonitorlogServiceIMPL monitorlogServiceIMPL;

    private static final Logger logger = LoggerFactory.getLogger(MonitorLogController.class);

    @PostMapping("/update")
    public ResponseEntity<String> updateMonitoringLog(@RequestBody MonitorlogDTO monitorlogDTO) {
        try {
            monitorlogServiceIMPL.updateMonitoringLog(monitorlogDTO);
            return ResponseEntity.ok("Monitoring log updated successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<MonitorlogDTO>> getAllMonitoringLogs() {
        List<MonitorlogDTO> logs = monitorlogServiceIMPL.getAllMonitoringLogs();
        logger.info("Get all monitoring logs successfully.");
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/generateCode")
    public String generateCode(@RequestParam("prefix") String prefix) {
        logger.info("MonitorLog generate code successfully.");
        return monitorlogServiceIMPL.nextCode(prefix);
    }

    @GetMapping("/detail")
    public ResponseEntity<List<CropDetailDTO>> getAllCrops() {
        List<CropDetailDTO> crops = monitorlogServiceIMPL.getAllCropsDetail();
        logger.info("Get all crops successfully.");
        return ResponseEntity.ok(crops);
    }

}

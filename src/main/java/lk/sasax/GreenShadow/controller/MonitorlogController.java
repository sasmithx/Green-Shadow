package lk.sasax.GreenShadow.controller;

import lk.sasax.GreenShadow.dto.CropDetailDTO;
import lk.sasax.GreenShadow.dto.FieldDTO;
import lk.sasax.GreenShadow.dto.MonitorlogDTO;
import lk.sasax.GreenShadow.service.impl.MonitorlogServiceIMPL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/log")
public class MonitorlogController {

    @Autowired
    MonitorlogServiceIMPL monitorlogServiceIMPL;

    private static final Logger logger = LoggerFactory.getLogger(MonitorlogController.class);

    @PostMapping("/save")
    public ResponseEntity<Void> saveLog(@RequestBody MonitorlogDTO monitorlogDTO) {
        monitorlogServiceIMPL.saveLog(monitorlogDTO);
        logger.info("Log Saved Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateMonitoringLog(@PathVariable("id") String id, @RequestBody MonitorlogDTO monitorlogDTO, FieldDTO fieldDTO) {
        try {
            monitorlogServiceIMPL.updateLog(id,monitorlogDTO,fieldDTO);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (IllegalArgumentException e) {
            logger.error("Error updating Monitoring log", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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

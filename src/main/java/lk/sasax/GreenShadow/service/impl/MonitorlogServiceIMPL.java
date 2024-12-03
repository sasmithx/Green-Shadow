package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.CropDetailDTO;
import lk.sasax.GreenShadow.dto.FieldDTO;
import lk.sasax.GreenShadow.dto.MonitorlogDTO;
import lk.sasax.GreenShadow.entity.Field;
import lk.sasax.GreenShadow.entity.MonitoringLogService;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.repository.*;
import lk.sasax.GreenShadow.service.MoniterLogService;
import lk.sasax.GreenShadow.util.Enum.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonitorlogServiceIMPL implements MoniterLogService {

    @Autowired
    CropRepository cropRepo;

    @Autowired
    FieldRepository fieldRepo;

    @Autowired
    private MonitorLogRepository monitoringLogRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private CropDetailRepository cropDetailRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void saveLog(MonitorlogDTO monitorlogDTO){
        monitorlogDTO.setLogCode(nextCode("LOG-"));

        MonitoringLogService savedLog =
                monitoringLogRepository.save(modelMapper.map(monitorlogDTO, MonitoringLogService.class));
        if(savedLog == null){
            System.out.println("Log not saved");
        }

    }

    @Override
    public void updateLog(String id, MonitorlogDTO monitorlogDTO, FieldDTO fieldDTO){
        MonitoringLogService log = monitoringLogRepository.findByLogCode(id)
                .orElseThrow(() -> new NotFoundException("Log not found"));
        log.setLogDate(monitorlogDTO.getLogDate());
        log.setLogDetails(monitorlogDTO.getLogDetails());
        log.setRole(UserRole.valueOf(monitorlogDTO.getRole()));

        Field field = fieldRepo.findByFieldCode(fieldDTO.getFieldCode())
                .orElseThrow(() -> new NotFoundException("Field not found"));
        log.setField(field);

        monitoringLogRepository.save(modelMapper.map(log, MonitoringLogService.class));

    }

    @Override
    public List<MonitorlogDTO> getAllMonitoringLogs() {
        List<MonitoringLogService> logs = monitoringLogRepository.findAll();

        return logs.stream().map(log -> {
            MonitorlogDTO dto = new MonitorlogDTO();
            dto.setLogCode(log.getLogCode());
            dto.setLogDate(LocalDate.parse(log.getLogDate().toString()));
            dto.setLogDetails(log.getLogDetails());
            dto.setRole(log.getRole().name());
            dto.setFieldCode(log.getField().getFieldCode());

            List<CropDetailDTO> cropDetailsDTOs = log.getCropDetails().stream().map(cropDetail -> {
                CropDetailDTO cropDetailDTO = new CropDetailDTO();
                cropDetailDTO.setLogCode(cropDetail.getLogCode());
                cropDetailDTO.setCropCode(cropDetail.getCrop_code());
                cropDetailDTO.setStaffId(cropDetail.getStaff_id());
                cropDetailDTO.setQuantity(cropDetail.getQuantity());
                cropDetailDTO.setMembersInStaff(cropDetail.getMembersInStaff());
                return cropDetailDTO;
            }).collect(Collectors.toList());

            dto.setCropDetails(cropDetailsDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

    public List<CropDetailDTO> getAllCropsDetail() {
        return cropDetailRepo.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropDetailDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String nextCode(String prefix) {
        long count = monitoringLogRepository.count();
        String nextInventoryCode = prefix + String.format("%03d", count + 1);
        return nextInventoryCode;
    }
}

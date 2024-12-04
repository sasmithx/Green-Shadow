package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.CropDetailDTO;
import lk.sasax.GreenShadow.dto.FieldDTO;
import lk.sasax.GreenShadow.dto.MonitorlogDTO;
import lk.sasax.GreenShadow.entity.*;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.repository.*;
import lk.sasax.GreenShadow.service.MoniterLogService;
import lk.sasax.GreenShadow.util.Enum.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

    /*@Override
    public void saveLog(MonitorlogDTO monitorlogDTO){
        monitorlogDTO.setLogCode(nextCode("LOG-"));

        MonitoringLogService savedLog =
                monitoringLogRepository.save(modelMapper.map(monitorlogDTO, MonitoringLogService.class));
        if(savedLog == null){
            System.out.println("Log not saved");
        }

    }*/

    /*@Override
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

    }*/


    @Transactional
    public void updateMonitoringLog(MonitorlogDTO monitorlogDTO) {
        // If logCode is not provided, generate a new one
        if (monitorlogDTO.getLogCode() == null || monitorlogDTO.getLogCode().isEmpty()) {
            monitorlogDTO.setLogCode(nextCode("LOG-"));
        }

        // Fetch the Field entity using the fieldCode
        Field field = fieldRepo.findById(monitorlogDTO.getFieldCode())
                .orElseThrow(() -> new IllegalArgumentException("Field not found"));

        // Check if the MonitoringLogService entity already exists; if not, create a new one
        MonitoringLogService logService = monitoringLogRepository.findById(monitorlogDTO.getLogCode())
                .orElseGet(() -> {
                    // Create a new logService if it does not exist
                    MonitoringLogService newLogService = new MonitoringLogService();
                    newLogService.setLogCode(monitorlogDTO.getLogCode());
                    newLogService.setLogDate(monitorlogDTO.getLogDate());
                    newLogService.setLogDetails(monitorlogDTO.getLogDetails());
                    newLogService.setRole(UserRole.valueOf(monitorlogDTO.getRole()));
                    newLogService.setField(field);  // Set the Field object here
                    // Initialize the cropDetails list if it's null
                    newLogService.setCropDetails(new ArrayList<>());
                    // Save the new MonitoringLogService entity
                    monitoringLogRepository.save(newLogService);
                    return newLogService;
                });

        // Iterate through each CropDetailDTO to update the crop quantity and staff quantity
        for (CropDetailDTO cropDetailDTO : monitorlogDTO.getCropDetails()) {
            // Find Crop and Staff entities by their codes
            Crop crop = cropRepo.findById(cropDetailDTO.getCropCode())
                    .orElseThrow(() -> new IllegalArgumentException("Crop not found"));

            Staff staff = staffRepository.findById(cropDetailDTO.getStaffId())
                    .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

            // Update the crop quantity (decrease it)
            int updatedCropQuantity = crop.getQty() - cropDetailDTO.getQuantity();
            if (updatedCropQuantity < 0) {
                throw new IllegalArgumentException("Not enough crop quantity available");
            }
            crop.setQty(updatedCropQuantity);

            // Update the staff quantity (decrease the number of members)
            int updatedStaffMembers = staff.getMembers() - cropDetailDTO.getMembersInStaff();
            if (updatedStaffMembers < 0) {
                throw new IllegalArgumentException("Not enough staff members available");
            }
            staff.setMembers(updatedStaffMembers);

            // Save updated entities back to the database
            cropRepo.save(crop);
            staffRepository.save(staff);

            // Optionally, create a new CropDetails record
            CropDetails cropDetails = new CropDetails();
            cropDetails.setLogCode(logService.getLogCode());  // Set logCode from logService
            cropDetails.setCrop_code(cropDetailDTO.getCropCode());
            cropDetails.setStaff_id(cropDetailDTO.getStaffId());
            cropDetails.setQuantity(cropDetailDTO.getQuantity());
            cropDetails.setMembersInStaff(cropDetailDTO.getMembersInStaff());

            // Set the log service reference
            cropDetails.setLogService(logService);

            // Save the cropDetails entry to the database
            cropDetailRepo.save(cropDetails);  // Assuming you have a cropDetailsRepo

            // Add CropDetails to the MonitoringLogService entity
            logService.getCropDetails().add(cropDetails);
        }

        // Save the updated MonitoringLogService (this ensures the logCode and related data are saved)
        monitoringLogRepository.save(logService);  // Save the MonitoringLogService to the DB
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

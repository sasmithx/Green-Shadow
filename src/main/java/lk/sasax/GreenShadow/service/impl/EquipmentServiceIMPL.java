package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.EquipmentDTO;
import lk.sasax.GreenShadow.entity.Equipment;
import lk.sasax.GreenShadow.entity.Field;
import lk.sasax.GreenShadow.entity.Staff;
import lk.sasax.GreenShadow.exception.DataPersistFailedException;
import lk.sasax.GreenShadow.exception.DuplicateRecordException;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.repository.EquipmentRepository;
import lk.sasax.GreenShadow.repository.FieldRepository;
import lk.sasax.GreenShadow.repository.StaffRepository;
import lk.sasax.GreenShadow.service.EquipmentService;
import lk.sasax.GreenShadow.util.Enum.EquipmentTypes;
import lk.sasax.GreenShadow.util.Enum.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipmentServiceIMPL implements EquipmentService {

  @Autowired
  EquipmentRepository equipmentRepository;
  @Autowired
  FieldRepository fieldRepository;
  @Autowired
  StaffRepository staffRepository;

  @Autowired
  ModelMapper mapper;

    @Override
    public EquipmentDTO saveEquipment(EquipmentDTO equipmentDTO) {

        Equipment equipment = new Equipment();
        equipment.setName(equipmentDTO.getName());
        equipment.setType(EquipmentTypes.valueOf(equipmentDTO.getType()));
        equipment.setStatus(Status.valueOf(equipmentDTO.getStatus()));
        equipment.setEquntity(equipmentDTO.getEquantity());

        fieldRepository.findByFieldCode(equipmentDTO.getAssignedFieldCode())
                .ifPresent(equipment::setAssignedField);

        Optional.ofNullable(staffRepository.findByStaffId(equipmentDTO.getAssignedStaffId()))
                .ifPresent(equipment::setAssignedStaff);

        equipment.setEquipmentId(generateNextEquipmentCode());

        return mapper.map(equipmentRepository.save(equipment), EquipmentDTO.class);
    }


    @Override
    public void updateEquipment(EquipmentDTO equipmentDTO) {
        Equipment existingEquipment = equipmentRepository.findById(equipmentDTO.getEquipmentId())
                .orElseThrow(() -> new NotFoundException("Equipment not found: " + equipmentDTO.getEquipmentId()));

        Optional.ofNullable(equipmentDTO.getName()).ifPresent(existingEquipment::setName);
        Optional.ofNullable(equipmentDTO.getType()).ifPresent(type -> existingEquipment.setType(EquipmentTypes.valueOf(type)));
        Optional.ofNullable(equipmentDTO.getStatus()).ifPresent(status -> existingEquipment.setStatus(Status.valueOf(status)));
        Optional.ofNullable(equipmentDTO.getEquantity()).ifPresent(existingEquipment::setEquntity);

        fieldRepository.findByFieldCode(equipmentDTO.getAssignedFieldCode())
                .ifPresent(existingEquipment::setAssignedField);
        Optional.ofNullable(staffRepository.findByStaffId(equipmentDTO.getAssignedStaffId()))
                .ifPresent(existingEquipment::setAssignedStaff);

        equipmentRepository.save(existingEquipment);
    }


    @Override
    public void deleteEquipment(String equipmentId) {
        equipmentRepository.deleteById(equipmentId);
    }


    @Override
    public List<EquipmentDTO> getAllEquipments() {
        return equipmentRepository.findAll().stream()
                .map(equipment -> {
                    EquipmentDTO equipmentDTO = mapper.map(equipment, EquipmentDTO.class);
                    equipmentDTO.setEquantity(equipment.getEquntity());
                    return equipmentDTO;
                })
                .collect(Collectors.toList());
    }


    @Override
    public String generateNextEquipmentCode() {
        String latestEquipmentId = equipmentRepository.findLatestEquipmentId();
        if(latestEquipmentId==null){latestEquipmentId = "EQ00";}
        int numericPart = Integer.parseInt(latestEquipmentId.substring(3));
        numericPart++;
        String nextEquipmentCode = "EQ-" + String.format("%03d", numericPart);
        return nextEquipmentCode;
    }

    @Override
    public long getEquipmentCount() {
        return equipmentRepository.count();
    }

}

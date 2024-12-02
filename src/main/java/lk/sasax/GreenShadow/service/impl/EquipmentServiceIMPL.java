package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.EquipmentDTO;
import lk.sasax.GreenShadow.entity.Equipment;
import lk.sasax.GreenShadow.entity.Field;
import lk.sasax.GreenShadow.entity.Staff;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class EquipmentServiceIMPL implements EquipmentService {

  @Autowired
  EquipmentRepository eqRepo;
  @Autowired
  FieldRepository frepo;
  @Autowired
  StaffRepository staffRepo;

  @Autowired
    ModelMapper mapper;


    public List<EquipmentDTO> getAllEq() {
        return eqRepo.findAll().stream()
                .map(v -> {
                    EquipmentDTO eqDTO = mapper.map(v, EquipmentDTO.class);
                    eqDTO.setEquantity(v.getEquntity());
                    return eqDTO;
                })
                .collect(Collectors.toList());
    }



    public EquipmentDTO saveEq(EquipmentDTO eDTO) {
        if (eqRepo.existsByEquipmentId(eDTO.getEquipmentId())) {
            throw new NotFoundException("This equipment ID " + eDTO.getEquipmentId() + " already exists.");
        }

        Equipment equipment = new Equipment();
        equipment.setName(eDTO.getName());
        equipment.setType(EquipmentTypes.valueOf(eDTO.getType()));
        equipment.setStatus(Status.valueOf(eDTO.getStatus()));
        equipment.setEquntity(eDTO.getEquantity());

        Field field = frepo.findByFieldCode(eDTO.getAssignedFieldCode()).orElse(null);
        if (field != null) {
            equipment.setAssignedField(field);
        }



                Staff staff = staffRepo.findByStaffId(eDTO.getAssignedStaffId());
        if (staff != null) {
            equipment.setAssignedStaff(staff);
        }

        eDTO.setEquipmentId(genarateNextEcode());
        equipment.setEquipmentId(eDTO.getEquipmentId());


        Equipment savedEquipment = eqRepo.save(equipment);
        return mapper.map(savedEquipment, EquipmentDTO.class);
    }



    public void updateEq(EquipmentDTO c) {

       Equipment map = mapper.map(c, Equipment.class);
        eqRepo.save(map);

    }


    public void deleteEq(String sid) {
        eqRepo.deleteById(sid);
    }



    public String genarateNextEcode() {
        String lastCustomerCode = eqRepo.findLatestEqId();
        if(lastCustomerCode==null){lastCustomerCode = "EQ00";}
        int numericPart = Integer.parseInt(lastCustomerCode.substring(3));
        numericPart++;
        String nextSupplierCode = "EQ-" + String.format("%03d", numericPart);
        return nextSupplierCode;
    }

    @Override
    public long getEquipmentCount() {
        return eqRepo.count();
    }


}

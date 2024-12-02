package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.StaffDTO;
import lk.sasax.GreenShadow.entity.Staff;
import lk.sasax.GreenShadow.exception.DuplicateRecordException;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.repository.StaffRepository;
import lk.sasax.GreenShadow.service.StaffService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StaffServiceIMPL  implements StaffService {
    @Autowired
    ModelMapper mapper;

    @Autowired
    StaffRepository staffRepo;

    public List<StaffDTO> getAllCrops() {
        return staffRepo.findAll().stream()
                .map(crop -> mapper.map(crop, StaffDTO.class))
                .collect(Collectors.toList());
    }


    public StaffDTO saveStaff(StaffDTO sDTO) {
        if(staffRepo.existsByStaffId(sDTO.getStaffId())){
            throw new DuplicateRecordException("This StaffId "+sDTO.getStaffId()+" already exists");
        }
        sDTO.setStaffId(genarateNextStaffCode());
        return mapper.map(staffRepo.save(mapper.map(
                sDTO, Staff.class)), StaffDTO.class
        );
    }


    public void updateStaff(StaffDTO staffDTO) {
        Staff map = mapper.map(staffDTO, Staff.class);
        staffRepo.save(map);
    }


    public void deleteStaff(String sid) {
      staffRepo.deleteById(sid);
    }


    public String genarateNextStaffCode() {
        String lastCustomerCode = staffRepo.findLatestStaffId();
        if(lastCustomerCode==null){lastCustomerCode = "ST00";}
        int numericPart = Integer.parseInt(lastCustomerCode.substring(3));
        numericPart++;
        String nextSupplierCode = "ST-" + String.format("%03d", numericPart);
        return nextSupplierCode;
    }

    @Override
    public long getStaffCount() {
        return staffRepo.count();
    }
}

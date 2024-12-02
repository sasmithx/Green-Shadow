package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.StaffDTO;
import lk.sasax.GreenShadow.entity.Staff;
import lk.sasax.GreenShadow.exception.DuplicateRecordException;
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
    StaffRepository staffRepository;

    public StaffDTO saveStaff(StaffDTO sDTO) {
        if(staffRepository.existsByStaffId(sDTO.getStaffId())){
            throw new DuplicateRecordException("This StaffId "+sDTO.getStaffId()+" already exists");
        }
        sDTO.setStaffId(genarateNextStaffCode());
        return mapper.map(staffRepository.save(mapper.map(
                sDTO, Staff.class)), StaffDTO.class
        );
    }

    public void updateStaff(StaffDTO staffDTO) {
        Staff map = mapper.map(staffDTO, Staff.class);
        staffRepository.save(map);
    }

    public void deleteStaff(String sid) {
      staffRepository.deleteById(sid);
    }

    public String genarateNextStaffCode() {
        String latestStaffCode= staffRepository.findLatestStaffId();
        if(latestStaffCode==null){latestStaffCode = "ST00";}
        int numericPart = Integer.parseInt(latestStaffCode.substring(3));
        numericPart++;
        String nextStaffCode = "ST-" + String.format("%03d", numericPart);
        return nextStaffCode;
    }

    public List<StaffDTO> getAllCrops() {
        return staffRepository.findAll().stream()
                .map(crop -> mapper.map(crop, StaffDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public long getStaffCount() {
        return staffRepository.count();
    }
}

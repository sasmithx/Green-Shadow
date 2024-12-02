package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    List<StaffDTO> getAllCrops() ;

    StaffDTO saveStaff(StaffDTO sDTO) ;

    void updateStaff(StaffDTO c) ;

    void deleteStaff(String id) ;

    StaffDTO searchStaff(String id) ;

    String genarateNextStaffCode() ;

    long getStaffCount() ;
}

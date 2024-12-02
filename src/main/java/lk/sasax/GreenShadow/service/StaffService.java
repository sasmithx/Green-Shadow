package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.StaffDTO;

import java.util.List;

public interface StaffService {

    StaffDTO saveStaff(StaffDTO sDTO) ;

    void updateStaff(StaffDTO c) ;

    void deleteStaff(String id) ;

    List<StaffDTO> getAllCrops() ;

    String genarateNextStaffCode() ;

    long getStaffCount() ;
}

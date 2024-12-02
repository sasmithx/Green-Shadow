package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.StaffDTO;

import java.util.List;

public interface StaffService {

    StaffDTO saveStaff(StaffDTO staffDTO) ;

    void updateStaff(StaffDTO staffDTO) ;

    void deleteStaff(String staffId) ;

    List<StaffDTO> getAllCrops() ;

    String generateNextStaffCode() ;

    long getStaffCount() ;
}

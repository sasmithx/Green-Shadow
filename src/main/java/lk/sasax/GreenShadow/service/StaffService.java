package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.StaffDTO;

import java.util.List;

public interface StaffService {
    public List<StaffDTO> getAllCrops() ;


    public StaffDTO saveStaff(StaffDTO sDTO) ;


    public void updateStaff(StaffDTO c) ;

    public void deleteStaff(String id) ;


    public StaffDTO searchStaff(String id) ;


    public String genarateNextStaffCode() ;
}

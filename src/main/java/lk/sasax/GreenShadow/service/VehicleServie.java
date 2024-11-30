package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.VechielDTO;

import java.util.List;

public interface VehicleServie {

    public List<VechielDTO> getAllVehecl() ;




    public VechielDTO saveVehicle(VechielDTO vDTO) ;


    public void updateVehicle(VechielDTO c) ;

    public void deleteVehicle(String sid);


    public VechielDTO searchVehicle(String id) ;

    public String genarateNextVcode();
}

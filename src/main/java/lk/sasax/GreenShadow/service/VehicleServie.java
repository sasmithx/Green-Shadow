package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.VehicleDTO;

import java.util.List;

public interface VehicleServie {

    public List<VehicleDTO> getAllVehecl() ;




    public VehicleDTO saveVehicle(VehicleDTO vDTO) ;


    public void updateVehicle(VehicleDTO c) ;

    public void deleteVehicle(String sid);


    public VehicleDTO searchVehicle(String id) ;

    public String genarateNextVcode();
}

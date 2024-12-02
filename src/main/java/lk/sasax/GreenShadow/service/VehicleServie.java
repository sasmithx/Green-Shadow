package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.VehicleDTO;

import java.util.List;

public interface VehicleServie {

    List<VehicleDTO> getAllVehecl() ;

    VehicleDTO saveVehicle(VehicleDTO vDTO) ;

    void updateVehicle(VehicleDTO c) ;

    void deleteVehicle(String sid);

    VehicleDTO searchVehicle(String id) ;

    String genarateNextVcode();

    long getVehicleCount() ;
}

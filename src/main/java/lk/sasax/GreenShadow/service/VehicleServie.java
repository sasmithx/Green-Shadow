package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.VehicleDTO;

import java.util.List;

public interface VehicleServie {

    VehicleDTO saveVehicle(VehicleDTO vehicleDTO) ;

    void updateVehicle(VehicleDTO vehicleDTO) ;

    void deleteVehicle(String vehicleId) ;

    List<VehicleDTO> getAllVehicles() ;

    String genarateNextVcode();

    long getVehicleCount() ;
}

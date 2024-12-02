package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.EquipmentDTO;

import java.util.List;

public interface EquipmentService {

    EquipmentDTO saveEquipment(EquipmentDTO eDTO) ;

    void updateEquipment(EquipmentDTO c) ;

    void deleteEquipment(String sid);

    List<EquipmentDTO> getAllEquipments();

    String generateNextEquipmentCode() ;

    long getEquipmentCount();

}

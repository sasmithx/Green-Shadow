package lk.sasax.GreenShadow.service;


import lk.sasax.GreenShadow.dto.EquipmentDTO;

public interface EquipmentService {

    EquipmentDTO saveEq(EquipmentDTO eDTO) ;

    void updateEq(EquipmentDTO c) ;

    void deleteEq(String sid);

    String genarateNextEcode() ;

    long getEquipmentCount();

}

package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.VechielDTO;
import lk.sasax.GreenShadow.entity.Staff;
import lk.sasax.GreenShadow.entity.Vehicle;
import lk.sasax.GreenShadow.exception.NotFoundException;
import lk.sasax.GreenShadow.repository.StaffRepository;
import lk.sasax.GreenShadow.repository.VechicleRepository;
import lk.sasax.GreenShadow.service.VehicleServie;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehicleServiceIMPL implements VehicleServie {

    @Autowired
    ModelMapper mapper;

    @Autowired
    VechicleRepository vechicleRepo;

    @Autowired
    StaffRepository staffRepo;

    public List<VechielDTO> getAllVehecl() {
        return vechicleRepo.findAll().stream()
                .map(v -> mapper.map(v, VechielDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public VechielDTO saveVehicle(VechielDTO vDTO) {
        Vehicle vehicle=new Vehicle();

        if(vechicleRepo.existsByVehicleCode(vDTO.getVehicleCode())){
            throw new NotFoundException("This vId "+vDTO.getVehicleCode()+" already exicts...");
        }

        Staff staff = staffRepo.findByStaffId(vDTO.getAllocatedStaffId());
        if (staff != null) {
            vehicle.setAllocatedStaff(staff);
        }
        vDTO.setVehicleCode(genarateNextVcode());
        return mapper.map(vechicleRepo.save(mapper.map(
                vDTO, Vehicle.class)), VechielDTO.class
        );
    }

    @Override
    public void updateVehicle(VechielDTO c) {
        Vehicle map = mapper.map(c, Vehicle.class);
        vechicleRepo.save(map);

    }

    @Override
    public void deleteVehicle(String sid) {
        vechicleRepo.deleteById(sid);
    }


    @Override
    public VechielDTO searchVehicle(String id) {

        Vehicle vehicle=new Vehicle();

        if(!vechicleRepo.existsByVehicleCode(id)){
            throw new NotFoundException("Vid Id "+id+" Not Found!");
        }
        return mapper.map(vechicleRepo.findByVehicleCode(id), VechielDTO.class);
    }


    @Override
    public String genarateNextVcode(){
        String lastCustomerCode = vechicleRepo.findLatestVehicleCode();
        if(lastCustomerCode==null){lastCustomerCode = "VE00";}
        int numericPart = Integer.parseInt(lastCustomerCode.substring(3));
        numericPart++;
        String nextSupplierCode = "VE-" + String.format("%03d", numericPart);
        return nextSupplierCode;
    }
}

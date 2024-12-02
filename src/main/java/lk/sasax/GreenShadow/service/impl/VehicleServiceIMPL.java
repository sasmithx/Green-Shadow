package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.VehicleDTO;
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
    VechicleRepository vechicleRepository;

    @Autowired
    StaffRepository staffRepository;

    @Override
    public VehicleDTO saveVehicle(VehicleDTO vehicleDTO) {
        Vehicle vehicle=new Vehicle();

        if(vechicleRepository.existsByVehicleCode(vehicleDTO.getVehicleCode())){
            throw new NotFoundException("Already Exists This "+vehicleDTO.getVehicleCode());
        }

        Staff staff = staffRepository.findByStaffId(vehicleDTO.getAllocatedStaffId());
        if (staff != null) {
            vehicle.setAllocatedStaff(staff);
        }
        vehicleDTO.setVehicleCode(genarateNextVcode());
        return mapper.map(vechicleRepository.save(mapper.map(
                vehicleDTO, Vehicle.class)), VehicleDTO.class
        );
    }

    @Override
    public void updateVehicle(VehicleDTO vehicleDTO) {
        Vehicle map = mapper.map(vehicleDTO, Vehicle.class);
        vechicleRepository.save(map);
    }

    @Override
    public void deleteVehicle(String sid) {
        vechicleRepository.deleteById(sid);
    }

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vechicleRepository.findAll().stream()
                .map(v -> mapper.map(v, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String genarateNextVcode(){
        String latestVehicleCode = vechicleRepository.findLatestVehicleCode();
        if(latestVehicleCode==null){latestVehicleCode = "VE00";}
        int numericPart = Integer.parseInt(latestVehicleCode.substring(3));
        numericPart++;
        String nextVehicleCode = "VE-" + String.format("%03d", numericPart);
        return nextVehicleCode;
    }

    @Override
    public long getVehicleCount() {
        return vechicleRepository.count();
    }
}

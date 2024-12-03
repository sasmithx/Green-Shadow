package lk.sasax.GreenShadow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lk.sasax.GreenShadow.dto.CropDetailDTO;
import lk.sasax.GreenShadow.entity.Crop;
import lk.sasax.GreenShadow.entity.CropDetails;
import lk.sasax.GreenShadow.entity.Staff;
import lk.sasax.GreenShadow.repository.CropDetailRepository;
import lk.sasax.GreenShadow.repository.CropRepository;
import lk.sasax.GreenShadow.repository.StaffRepository;
import lk.sasax.GreenShadow.service.CropDetailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CropDetailServiceIMPL implements CropDetailService {

    @Autowired
    ModelMapper mapper;

    @Autowired
    CropRepository cropRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    CropDetailRepository cropDetailRepository;


    /*@Override
    public CropDetailDTO saveCropDetail(CropDetailDTO cropDetailDTO) {
        return null;
    }*/

    /*@Transactional
    @Override
    public CropDetailDTO saveCropDetail(CropDetailDTO cropDetailDTO) {

        if (cropDetailDTO == null || cropDetailDTO.getCropCode() == null || cropDetailDTO.getStaffId() == null) {
            throw new IllegalArgumentException("CropDetailDTO or its mandatory fields cannot be null.");
        }

        Crop crop = cropRepository.findByCropCode(cropDetailDTO.getCropCode())
                .orElseThrow(() -> new EntityNotFoundException("Crop not found for code: " + cropDetailDTO.getCropCode()));

        Staff staff = staffRepository.findByStaffId(cropDetailDTO.getStaffId())
                .orElseThrow(() -> new EntityNotFoundException("Staff not found for code: " + cropDetailDTO.getStaffId()));

        crop.setQty(crop.getQty() + cropDetailDTO.getQuantity());
        cropRepository.save(crop);

        staff.setMembers(staff.getMembers() + cropDetailDTO.getMembersInStaff());
        staffRepository.save(staff);

        CropDetails cropDetail = mapper.map(cropDetailDTO, CropDetails.class);
        cropDetail = cropDetailRepository.save(cropDetail);

        return mapper.map(cropDetail, CropDetailDTO.class);
    }*/

    @Transactional
    @Override
    public CropDetailDTO saveCropDetail(CropDetailDTO cropDetailDTO) {

        if (cropDetailDTO == null || cropDetailDTO.getCropCode() == null || cropDetailDTO.getStaffId() == null) {
            throw new IllegalArgumentException("CropDetailDTO or its mandatory fields cannot be null.");
        }

        Crop crop = cropRepository.findByCropCode(cropDetailDTO.getCropCode());
        if (crop == null) {
            throw new EntityNotFoundException("Crop not found for code: " + cropDetailDTO.getCropCode());
        }

        Staff staff = staffRepository.findByStaffId(cropDetailDTO.getStaffId());
        if (staff == null) {
            throw new EntityNotFoundException("Staff not found for code: " + cropDetailDTO.getStaffId());
        }

        crop.setQty(crop.getQty() + cropDetailDTO.getQuantity());
        cropRepository.save(crop);

        staff.setMembers(staff.getMembers() + cropDetailDTO.getMembersInStaff());
        staffRepository.save(staff);

        CropDetails cropDetail = mapper.map(cropDetailDTO, CropDetails.class);

        cropDetail = cropDetailRepository.save(cropDetail);

        return mapper.map(cropDetail, CropDetailDTO.class);
    }


    @Override
    public void UpdateCropDetail(CropDetailDTO cropDetailDTO) {

    }

    @Override
    public List<CropDetailDTO> getAllCropDetails() {
        return List.of();
    }

    @Override
    public String generateNextCropDetailId() {
        return "";
    }
}

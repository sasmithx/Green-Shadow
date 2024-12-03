package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.dto.CropDetailDTO;

import java.util.List;

public interface CropDetailService {
    CropDetailDTO saveCropDetail(CropDetailDTO cropDetailDTO);
    void UpdateCropDetail(CropDetailDTO cropDetailDTO);
    List<CropDetailDTO> getAllCropDetails();
    String generateNextCropDetailId();
}

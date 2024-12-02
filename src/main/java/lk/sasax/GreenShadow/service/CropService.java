package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.dto.CropDTO;
import lk.sasax.GreenShadow.entity.Crop;

import java.io.IOException;
import java.util.List;

public interface CropService {
    List<CropDTO> getAllCrops();
    Crop saveCrop(CropDTO cropDTO) throws IOException;
    Crop updateCrop(String cropCode, CropDTO cropDTO) throws IOException;
    void deleteCrop(String Code);
    String generateCropCode();
    long getCropCount();
}

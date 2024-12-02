package lk.sasax.GreenShadow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.sasax.GreenShadow.dto.CropDTO;
import lk.sasax.GreenShadow.entity.Crop;
import lk.sasax.GreenShadow.repository.CropRepository;
import lk.sasax.GreenShadow.util.Enum.CropCategory;
import lk.sasax.GreenShadow.util.Enum.CropComnName;
import lk.sasax.GreenShadow.util.Enum.CropScienceName;
import lk.sasax.GreenShadow.util.Enum.CropSesasons;
import lk.sasax.GreenShadow.util.handle.FileUploader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CropServiceIMPL {


    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FileUploader fileUploader;



    private static final String CODE_PREFIX = "CR-";
    private static final int CODE_LENGTH = 3;

    public List<CropDTO> getAllCrops() {
        return cropRepository.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public Crop saveCrop(CropDTO cropDTO) throws IOException {
        String cropImagePath = null;
        if (cropDTO.getCropImage() != null && !cropDTO.getCropImage().isEmpty()) {
            cropImagePath = fileUploader.storeFile(cropDTO.getCropImage());
        }


        String generatedCropCode = generateCropCode();


        Crop crop = new Crop();
        crop.setCropCode(generatedCropCode);
        crop.setCropCommonName(CropComnName.valueOf(cropDTO.getCropCommonName()));
        crop.setCropScientificName(CropScienceName.valueOf(cropDTO.getCropScientificName()));
        crop.setCategory(CropCategory.valueOf(cropDTO.getCategory()));
        crop.setQty(cropDTO.getQty());
        crop.setCropSeason(CropSesasons.valueOf(cropDTO.getCropSeason()));
        crop.setFieldCodes(cropDTO.getFieldCodes());
        crop.setFiledNames(cropDTO.getFiledNames());


        crop.setCropImage(cropImagePath);


        return cropRepository.save(crop);
    }



    @Transactional
    public Crop updateCrop(String cropCode, CropDTO cropDTO) throws IOException {

        Optional<Crop> optionalCrop = Optional.ofNullable(cropRepository.findByCropCode(cropCode));


        if (!optionalCrop.isPresent()) {
            throw new EntityNotFoundException("Crop not found with code: " + cropCode);
        }

        Crop crop = optionalCrop.get();


        if (cropDTO.getCropCommonName() != null) {
            crop.setCropCommonName(CropComnName.valueOf(cropDTO.getCropCommonName()));
        }
        if (cropDTO.getCropScientificName() != null) {
            crop.setCropScientificName(CropScienceName.valueOf(cropDTO.getCropScientificName()));
        }
        if (cropDTO.getCategory() != null) {
            crop.setCategory(CropCategory.valueOf(cropDTO.getCategory()));
        }
        if (cropDTO.getQty() != 0) {
            crop.setQty(cropDTO.getQty());
        }
        if (cropDTO.getCropSeason() != null) {
            crop.setCropSeason(CropSesasons.valueOf(cropDTO.getCropSeason()));
        }
        if (cropDTO.getFieldCodes() != null) {
            crop.setFieldCodes(cropDTO.getFieldCodes());
        }
        if (cropDTO.getFiledNames() != null) {
            crop.setFiledNames(cropDTO.getFiledNames());
        }


        if (cropDTO.getCropImage() != null && !cropDTO.getCropImage().isEmpty()) {
            String cropImagePath = fileUploader.storeFile(cropDTO.getCropImage());
            crop.setCropImage(cropImagePath);
        }


        return cropRepository.save(crop);
    }



    public void deleteCrop(String Code) {
        cropRepository.deleteById(Code);
    }


    public String generateCropCode() {
        long count = cropRepository.count() + 1;
        return String.format("CR-%03d", count);
    }

    public long getCropCount() {
        return cropRepository.count();
    }


}

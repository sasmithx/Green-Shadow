package lk.sasax.GreenShadow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.sasax.GreenShadow.dto.CropDTO;
import lk.sasax.GreenShadow.entity.Crop;
import lk.sasax.GreenShadow.repository.CropRepository;
import lk.sasax.GreenShadow.service.CropService;
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
public class CropServiceIMPL implements CropService {


    @Autowired
    private CropRepository cropRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FileUploader fileUploader;

    @Transactional
    @Override
    public Crop saveCrop(CropDTO cropDTO) throws IOException {
        String cropImage = null;
        if (cropDTO.getCropImage() != null && !cropDTO.getCropImage().isEmpty()) {
            cropImage = fileUploader.storeFile(cropDTO.getCropImage());
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
        crop.setCropImage(cropImage);
        return cropRepository.save(crop);
    }



    @Transactional
    @Override
    public Crop updateCrop(String cropCode, CropDTO cropDTO) throws IOException {

        Optional<Crop> optionalCrop = Optional.ofNullable(cropRepository.findByCropCode(cropCode));

        if (!optionalCrop.isPresent()) {
            throw new EntityNotFoundException("Crop not found with code: " + cropCode);
        }

        Crop crop = optionalCrop.get();
        crop.setCropCommonName(CropComnName.valueOf(cropDTO.getCropCommonName()));
        crop.setCropScientificName(CropScienceName.valueOf(cropDTO.getCropScientificName()));
        crop.setCategory(CropCategory.valueOf(cropDTO.getCategory()));
        crop.setQty(cropDTO.getQty());
        crop.setCropSeason(CropSesasons.valueOf(cropDTO.getCropSeason()));
        crop.setFieldCodes(cropDTO.getFieldCodes());
        crop.setFiledNames(cropDTO.getFiledNames());
        String cropImage = fileUploader.storeFile(cropDTO.getCropImage());
        crop.setCropImage(cropImage);
        return cropRepository.save(crop);
    }


    @Override
    public void deleteCrop(String Code) {
        cropRepository.deleteById(Code);
    }

    @Override
    public List<CropDTO> getAllCrops() {
        return cropRepository.findAll().stream()
                .map(crop -> modelMapper.map(crop, CropDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String generateCropCode() {
        long count = cropRepository.count() + 1;
        return String.format("CR-%03d", count);
    }

    @Override
    public long getCropCount() {
        return cropRepository.count();
    }

}

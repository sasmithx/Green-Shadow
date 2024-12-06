package lk.sasax.GreenShadow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lk.sasax.GreenShadow.dto.CropDTO;
import lk.sasax.GreenShadow.entity.Crop;
import lk.sasax.GreenShadow.repository.CropRepository;
import lk.sasax.GreenShadow.service.CropService;
import lk.sasax.GreenShadow.util.Enum.CropCategory;
import lk.sasax.GreenShadow.util.Enum.CropCommonName;
import lk.sasax.GreenShadow.util.Enum.CropScienceName;
import lk.sasax.GreenShadow.util.Enum.CropSesasons;
import lk.sasax.GreenShadow.util.handle.ImageHandle;
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
    ImageHandle imageHandle;

    @Transactional
    @Override
    public Crop saveCrop(CropDTO cropDTO) throws IOException {
        String cropImage = null;
        if (cropDTO.getCropImage() != null && !cropDTO.getCropImage().isEmpty()) {
            cropImage = imageHandle.storeFile(cropDTO.getCropImage());
        }

        String generatedCropCode = generateCropCode();
        Crop crop = new Crop();
        crop.setCropCode(generatedCropCode);
        crop.setCropCommonName(CropCommonName.valueOf(cropDTO.getCropCommonName()));
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
        Crop crop = Optional.ofNullable(cropRepository.findByCropCode(cropCode))
                .orElseThrow(() -> new EntityNotFoundException("Crop not found : " + cropCode));

        Optional.ofNullable(cropDTO.getCropCommonName())
                .ifPresent(name -> crop.setCropCommonName(CropCommonName.valueOf(name)));
        Optional.ofNullable(cropDTO.getCropScientificName())
                .ifPresent(name -> crop.setCropScientificName(CropScienceName.valueOf(name)));
        Optional.ofNullable(cropDTO.getCategory())
                .ifPresent(category -> crop.setCategory(CropCategory.valueOf(category)));
        if (cropDTO.getQty() != 0) crop.setQty(cropDTO.getQty());
        Optional.ofNullable(cropDTO.getCropSeason())
                .ifPresent(season -> crop.setCropSeason(CropSesasons.valueOf(season)));
        Optional.ofNullable(cropDTO.getFieldCodes()).ifPresent(crop::setFieldCodes);
        Optional.ofNullable(cropDTO.getFiledNames()).ifPresent(crop::setFiledNames);
        Optional.ofNullable(cropDTO.getCropImage())
                .filter(image -> !image.isEmpty())
                .ifPresent(image -> {
                    try {
                        crop.setCropImage(imageHandle.storeFile(image));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

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

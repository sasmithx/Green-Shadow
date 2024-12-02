package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.FieldDTO;
import lk.sasax.GreenShadow.entity.Crop;
import lk.sasax.GreenShadow.entity.Field;
import lk.sasax.GreenShadow.entity.Staff;
import lk.sasax.GreenShadow.repository.CropRepository;
import lk.sasax.GreenShadow.repository.FieldRepository;
import lk.sasax.GreenShadow.repository.StaffRepository;
import lk.sasax.GreenShadow.service.FieldService;
import lk.sasax.GreenShadow.util.Enum.Locations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FieldServiceIMPL implements FieldService {

    @Autowired
    FieldRepository fieldRepository;
    @Autowired
    CropRepository cropRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    ModelMapper mapper;
    @Value("${field.images.directory}")
    private String imgDirectory;

    @Override
    public FieldDTO saveField(FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException {
        Field field = new Field();
        field.setFieldCode(generateFieldCode());
        field.setFieldName(fieldDTO.getFieldName());
        field.setFieldLocation(Locations.valueOf(fieldDTO.getFieldLocation()));
        field.setSize(fieldDTO.getSize());
        field.setNameOfCrop(fieldDTO.getNameOfCrop());

        Optional.ofNullable(cropRepository.findByCropCode(fieldDTO.getCropCode())).ifPresent(field::setCrops);
        Optional.ofNullable(staffRepository.findByStaffId(fieldDTO.getStaffId())).ifPresent(field::setStaff);

        Optional.ofNullable(fieldImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    try {
                        field.setFieldImage1(saveImageToDirectory(file, field.getFieldCode()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        Field savedField = fieldRepository.save(field);

        return new FieldDTO(
                savedField.getFieldCode(),
                savedField.getFieldName(),
                savedField.getFieldLocation().name(),
                savedField.getSize(),
                Optional.ofNullable(savedField.getCrops()).map(Crop::getCropCode).orElse("Crop not associated"),
                savedField.getNameOfCrop(),
                Optional.ofNullable(savedField.getStaff()).map(Staff::getStaffId).orElse(null),
                savedField.getFieldImage1()
        );
    }

    @Override
    public FieldDTO updateField(String fieldCode, FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException {
        Field field = fieldRepository.findByFieldCode(fieldCode)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with code: " + fieldCode));

        Optional.ofNullable(fieldDTO.getFieldName()).ifPresent(field::setFieldName);
        Optional.ofNullable(fieldDTO.getFieldLocation())
                .ifPresent(location -> field.setFieldLocation(Locations.valueOf(location)));
        Optional.ofNullable(fieldDTO.getSize()).ifPresent(field::setSize);

        Optional.ofNullable(fieldDTO.getCropCode())
                .ifPresent(cropCode -> field.setCrops(
                        Optional.ofNullable(cropRepository.findByCropCode(cropCode))
                                .orElseThrow(() -> new IllegalArgumentException("Crop not found with code: " + cropCode))
                ));

        Optional.ofNullable(fieldDTO.getStaffId())
                .ifPresent(staffId -> field.setStaff(
                        Optional.ofNullable(staffRepository.findByStaffId(staffId))
                                .orElseThrow(() -> new IllegalArgumentException("Staff not found with ID: " + staffId))
                ));

        Optional.ofNullable(fieldImageFile)
                .filter(file -> !file.isEmpty())
                .ifPresent(file -> {
                    try {
                        field.setFieldImage1(saveImageToDirectory(file, field.getFieldCode()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        Field updatedField = fieldRepository.save(field);

        return new FieldDTO(
                updatedField.getFieldCode(),
                updatedField.getFieldName(),
                updatedField.getFieldLocation().name(),
                updatedField.getSize(),
                Optional.ofNullable(updatedField.getCrops()).map(Crop::getCropCode).orElse("Crop not associated"),
                updatedField.getNameOfCrop(),
                Optional.ofNullable(updatedField.getStaff()).map(Staff::getStaffId).orElse(null),
                updatedField.getFieldImage1()
        );
    }

    public void deleteFiled(String employeeCode) {
        fieldRepository.deleteById(employeeCode);
    }

    @Override
    public List<FieldDTO> getAllFields() {
        return fieldRepository.findAll().stream()
                .map(field -> {
                    FieldDTO fieldDTO = new FieldDTO();
                    fieldDTO.setFieldCode(field.getFieldCode());
                    fieldDTO.setFieldName(field.getFieldName());
                    fieldDTO.setFieldLocation(field.getFieldLocation().name());
                    fieldDTO.setSize(field.getSize());
                    fieldDTO.setCropCode(Optional.ofNullable(field.getCrops()).map(Crop::getCropCode).orElse(null));
                    fieldDTO.setNameOfCrop(field.getNameOfCrop());
                    fieldDTO.setStaffId(Optional.ofNullable(field.getStaff()).map(Staff::getStaffId).orElse(null));
                    fieldDTO.setFieldImage1(field.getFieldImage1());
                    return fieldDTO;
                })
                .collect(Collectors.toList());
    }

    public String generateFieldCode() {
        long count = fieldRepository.count() + 1;
        return String.format("FI-%03d", count);
    }

    private String saveImageToDirectory(MultipartFile imageFile, String fieldCode) throws IOException {
        String fileName = fieldCode + "_" + imageFile.getOriginalFilename();
        String filePath = Paths.get(imgDirectory, fileName).toString();

        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageFile.getBytes());
        }
        return filePath;
    }

    @Override
    public long getFieldCount() {
        return fieldRepository.count();
    }
}

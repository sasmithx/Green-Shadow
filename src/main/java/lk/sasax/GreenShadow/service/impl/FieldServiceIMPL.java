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
import java.util.stream.Collectors;

@Service
@Transactional
public class FieldServiceIMPL implements FieldService {

    @Autowired
    FieldRepository fieldRepo;

    @Autowired
    CropRepository cropRepo;
    @Autowired
    StaffRepository staffrepo;

    @Autowired
    ModelMapper mapper;

    @Value("${field.images.directory}")
    private String imagesDirectory;



    public List<FieldDTO> getAllFields() {
        return fieldRepo.findAll().stream().map(field -> {
            FieldDTO fieldDTO = new FieldDTO();
            fieldDTO.setFieldCode(field.getFieldCode());
            fieldDTO.setFieldName(field.getFieldName());
            fieldDTO.setFieldLocation(field.getFieldLocation().name());
            fieldDTO.setSize(field.getSize());

            if (field.getCrops() != null) {
                fieldDTO.setCropCode(field.getCrops().getCropCode());
            } else {
                fieldDTO.setCropCode(null);
            }

            fieldDTO.setNameOfCrop(field.getNameOfCrop());
            fieldDTO.setStaffId(field.getStaff() != null ? field.getStaff().getStaffId() : null);
            fieldDTO.setFieldImage1(field.getFieldImage1());

            return fieldDTO;
        }).collect(Collectors.toList());
    }




    public FieldDTO saveField(FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException {
        Field field = new Field();


        field.setFieldCode(generateFieldCode());


        field.setFieldName(fieldDTO.getFieldName());
        field.setFieldLocation(Locations.valueOf(fieldDTO.getFieldLocation()));
        field.setSize(fieldDTO.getSize());


        Crop crop = cropRepo.findByCropCode(fieldDTO.getCropCode());
        if (crop != null) {
            field.setCrops(crop);
        }

        field.setNameOfCrop(fieldDTO.getNameOfCrop());


        Staff staff = staffrepo.findByStaffId(fieldDTO.getStaffId());
        if (staff != null) {
            field.setStaff(staff);
        }


        if (fieldImageFile != null && !fieldImageFile.isEmpty()) {
            String filePath = saveImageToDirectory(fieldImageFile, field.getFieldCode());
            field.setFieldImage1(filePath);
        }

        field = fieldRepo.save(field);

        FieldDTO savedFieldDTO = new FieldDTO();
        savedFieldDTO.setFieldCode(field.getFieldCode());
        savedFieldDTO.setFieldName(field.getFieldName());
        savedFieldDTO.setFieldLocation(field.getFieldLocation().name());
        savedFieldDTO.setSize(field.getSize());
        savedFieldDTO.setCropCode(field.getCrops() != null ? field.getCrops().getCropCode() : "Crop not associated");

        savedFieldDTO.setNameOfCrop(field.getNameOfCrop());
        savedFieldDTO.setStaffId(field.getStaff() != null ? field.getStaff().getStaffId() : null);
        savedFieldDTO.setFieldImage1(field.getFieldImage1());



        return savedFieldDTO;
    }

    public String generateFieldCode() {
        long count = fieldRepo.count() + 1;
        return String.format("FI-%03d", count);
    }


    private String saveImageToDirectory(MultipartFile imageFile, String fieldCode) throws IOException {
        String fileName = fieldCode + "_" + imageFile.getOriginalFilename();
        String filePath = Paths.get(imagesDirectory, fileName).toString();

        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(imageFile.getBytes());
        }
        return filePath;
    }





    public void deleteFiled(String employeeCode) {
        fieldRepo.deleteById(employeeCode);
    }

    public FieldDTO updateField(String fieldCode, FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException {
        Field field = fieldRepo.findByFieldCode(fieldCode)
                .orElseThrow(() -> new IllegalArgumentException("Field not found with code: " + fieldCode));


        if (fieldDTO.getFieldName() != null) field.setFieldName(fieldDTO.getFieldName());
        if (fieldDTO.getFieldLocation() != null) field.setFieldLocation(Locations.valueOf(fieldDTO.getFieldLocation()));
        if (fieldDTO.getSize() != null) field.setSize(fieldDTO.getSize());

        if (fieldDTO.getCropCode() != null) {
            Crop crop = cropRepo.findByCropCode(fieldDTO.getCropCode());
            if (crop != null) {
                field.setCrops(crop);
            } else {
                throw new IllegalArgumentException("Crop not found with code: " + fieldDTO.getCropCode());
            }
        }


        if (fieldDTO.getStaffId() != null) {
            Staff staff = staffrepo.findByStaffId(fieldDTO.getStaffId());
            if (staff != null) {
                field.setStaff(staff);
            } else {
                throw new IllegalArgumentException("StaffId not found with code: " + fieldDTO.getStaffId());
            }
        }


        if (fieldImageFile != null && !fieldImageFile.isEmpty()) {
            String filePath = saveImageToDirectory(fieldImageFile, field.getFieldCode());
            field.setFieldImage1(filePath);
        }

        field = fieldRepo.save(field);


        FieldDTO updatedFieldDTO = new FieldDTO();
        updatedFieldDTO.setFieldCode(field.getFieldCode());
        updatedFieldDTO.setFieldName(field.getFieldName());
        updatedFieldDTO.setFieldLocation(field.getFieldLocation().name());
        updatedFieldDTO.setSize(field.getSize());
        updatedFieldDTO.setCropCode(field.getCrops() != null ? field.getCrops().getCropCode() : "Crop not associated");
        updatedFieldDTO.setNameOfCrop(field.getNameOfCrop());
        updatedFieldDTO.setStaffId(field.getStaff() != null ? field.getStaff().getStaffId() : null);
        updatedFieldDTO.setFieldImage1(field.getFieldImage1());

        return updatedFieldDTO;
    }
}

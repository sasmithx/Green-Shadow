package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.dto.FieldDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FieldService {
    List<FieldDTO> getAllFields() ;

    FieldDTO saveField(FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException ;

    String generateFieldCode() ;

    void deleteFiled(String employeeCode);

    FieldDTO updateField(String fieldCode, FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException ;

    long getFieldCount() ;
}

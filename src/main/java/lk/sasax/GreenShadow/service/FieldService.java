package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.dto.FieldDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FieldService {

    FieldDTO saveField(FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException ;

    FieldDTO updateField(String fieldCode, FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException ;

    void deleteFiled(String employeeCode);

    List<FieldDTO> getAllFields() ;

    String generateFieldCode() ;

    long getFieldCount() ;
}

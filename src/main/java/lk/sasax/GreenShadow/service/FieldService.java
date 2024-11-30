package lk.sasax.GreenShadow.service;

import lk.sasax.GreenShadow.dto.FieldDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FieldService {
    public List<FieldDTO> getAllFields() ;

    public FieldDTO saveField(FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException ;

    public String generateFieldCode() ;

    public void deleteFiled(String employeeCode);

    public FieldDTO updateField(String fieldCode, FieldDTO fieldDTO, MultipartFile fieldImageFile) throws IOException ;
}

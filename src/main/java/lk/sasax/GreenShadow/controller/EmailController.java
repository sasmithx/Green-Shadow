package lk.sasax.GreenShadow.controller;

import jakarta.validation.Valid;
import lk.sasax.GreenShadow.dto.EmailRequest;
import lk.sasax.GreenShadow.service.impl.EmailServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    @Autowired
    private EmailServiceIMPL emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody EmailRequest emailRequest){
        return  ResponseEntity.ok(emailService.sendEmail(emailRequest));
    }
}

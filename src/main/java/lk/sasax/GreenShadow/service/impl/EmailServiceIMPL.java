package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.EmailDTO;
import lk.sasax.GreenShadow.entity.Email;
import lk.sasax.GreenShadow.repository.EmailRepository;
import lk.sasax.GreenShadow.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceIMPL implements EmailService {

    /*private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;*/

    public String sendEmail(EmailDTO emailDTO){
       /* try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(emailDTO.getTo());
            simpleMailMessage.setSubject(emailDTO.getSubject());
            simpleMailMessage.setText(emailDTO.getBody());
            javaMailSender.send(simpleMailMessage); //where we are sending email


            //SAVE TO DATABSE
            Email emailToSave = new Email();
            emailToSave.setRecipient(emailDTO.getTo());
            emailToSave.setSubject(emailDTO.getSubject());
            emailToSave.setBody(emailDTO.getBody());
            emailRepository.save(emailToSave); //where we are saving to database
            return "Email successfully sent";
        }catch (Exception e){
            return e.getMessage();
        }*/
        return null;
    }

}

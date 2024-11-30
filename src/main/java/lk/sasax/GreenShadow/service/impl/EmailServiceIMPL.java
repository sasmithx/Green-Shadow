package lk.sasax.GreenShadow.service.impl;

import lk.sasax.GreenShadow.dto.EmailRequest;
import lk.sasax.GreenShadow.repository.EmailRepository;
import lk.sasax.GreenShadow.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceIMPL implements EmailService {

    private final EmailRepository emailRepository;
    //private final JavaMailSender javaMailSender;

    public String sendEmail(EmailRequest emailRequest){
        /*try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setTo(emailRequest.getTo());
            simpleMailMessage.setSubject(emailRequest.getSubject());
            simpleMailMessage.setText(emailRequest.getBody());
            javaMailSender.send(simpleMailMessage); //wheere we are sending email


            //SAVE TO DATABSE
            Email emailToSave = new Email();
            emailToSave.setRecipient(emailRequest.getTo());
            emailToSave.setSubject(emailRequest.getSubject());
            emailToSave.setBody(emailRequest.getBody());
            emailRepository.save(emailToSave); //where we are saving to database
            return "Email successfully sent";
        }catch (Exception e){
            return e.getMessage();
        }*/
        return null;
    }

}

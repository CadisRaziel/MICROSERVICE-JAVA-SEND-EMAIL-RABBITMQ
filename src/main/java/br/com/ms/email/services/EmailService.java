package br.com.ms.email.services;

import br.com.ms.email.enums.StatusEmail;
import br.com.ms.email.models.EmailModel;
import br.com.ms.email.repositories.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        //podemos trocar o SMT do gmail pelo Amazon SS(Amazon Simple Email Service)
        //pegando senha de 16 digitos para configurar no application.properties (https://support.google.com/accounts/answer/185833)

        emailModel.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            // para salvar com o status error ou sent
            //com isso podemos implementar uma politica de tentativas de envio
            return emailRepository.save(emailModel);
        }
    }
}

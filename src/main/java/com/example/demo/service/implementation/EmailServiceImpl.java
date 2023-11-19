package com.example.demo.service.implementation;

import com.example.demo.enumeration.VerificationType;
import com.example.demo.exception.ApiException;
import com.example.demo.service.EmailService;
import jakarta.mail.internet.InternetAddress;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.Address;
import jakarta.mail.Message;
import org.springframework.mail.javamail.MimeMessagePreparator;
import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String firstName, String email, String verificationUrl, VerificationType verificationType) {

        MimeMessagePreparator preparator = mimeMessage -> {
            final Address recipient = new InternetAddress(email);
            mimeMessage.setFrom(new InternetAddress("dizvabole@gmail.com"));
            mimeMessage.setRecipient(Message.RecipientType.TO, recipient);
            mimeMessage.setSentDate(new Date());
            mimeMessage.setSubject(String.format("Invoice Management - %s Verification Email", StringUtils.capitalize(verificationType.getType())));
            mimeMessage.setText(getEmailMessage(firstName, verificationUrl, verificationType));
        };

        Thread t = Thread.currentThread();
        ClassLoader orig = t.getContextClassLoader();
        t.setContextClassLoader(InternetAddress.class.getClassLoader());
        try {
            mailSender.send(preparator);
            log.info("Email sent to {}", firstName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        } finally {
            t.setContextClassLoader(orig);
        }
    }


//    @Override
//    public void sendVerificationEmail(String firstName, String email, String verificationUrl, VerificationType verificationType) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom("dizvabole@gmail.com");
//            message.setTo(email);
//            message.setText(getEmailMessage(firstName, verificationUrl, verificationType));
//            message.setSubject(String.format("Invoice Management - %s Verification Email", StringUtils.capitalize(verificationType.getType())));
//            mailSender.send(message);
//            log.info("Email sent to {}", firstName);
//        } catch (Exception exception) {
//            log.error(exception.getMessage());
//        }
//    }

    private String getEmailMessage(String firstName, String verificationUrl, VerificationType verificationType) {
        switch (verificationType) {
            case PASSWORD -> {
                return "Hello " + firstName + "\n\nReset password request. Please click the link below to reset your password. \n\n" + verificationUrl + "\n\nThe Support Team";
            }
            case ACCOUNT -> {
                return "Hello " + firstName + "\n\nYour new account has been created. Please click the link below to verify your account. \n\n" + verificationUrl + "\n\nThe Support Team";
            }
            default -> throw new ApiException("Unable to send email. Unknown email");
        }
    }
}

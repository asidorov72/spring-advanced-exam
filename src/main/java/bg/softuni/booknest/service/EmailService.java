package bg.softuni.booknest.service;

import bg.softuni.booknest.model.dto.ContactRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${booknest.mail.from}")
    private String from;

    @Value("${booknest.mail.to}")
    private String to;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactMessage(ContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject("[BookNest Contact] " + request.getSubject());

        message.setText("""
                New contact message from BookNest.

                Name: %s
                Email: %s
                Subject: %s

                Message:
                %s
                """.formatted(
                request.getName(),
                request.getEmail(),
                request.getSubject(),
                request.getMessage()
        ));

        mailSender.send(message);
    }
}
package com.shreyas.SAS.Service;

import com.shreyas.SAS.DTO.RequestKey;
import com.shreyas.SAS.DTO.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    @Autowired
    RedisService redisService;
    @Autowired
    private JavaMailSender mailSender;
    public void sendEmail(String email, String courseName, double amount) {
        SimpleMailMessage msg=new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Payment Successful"+courseName);
        msg.setText("Hi new user" + ",\n\n" +
                "Thank you for enrolling in " + courseName + ".\n\n" +
                "We look forward to seeing you in the course!\n\n" +
                "Please join telegram group link mentioned below\n\n"+
                "Best regards,\n" +
                "The [Your Team/Company Name] Team");
        mailSender.send(msg);
    }
    public String sendOtpEmail(String toEmail, RequestType type) {
        // Generate 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(1_000_000));

        redisService.storeCommand(new RequestKey(type,toEmail),otp);
        System.out.println("OTP IS"+otp);
        // Determine subject and message based on type
        String subject = switch (type) {
            case LOGIN -> "Your Login OTP";
            case VERIFY_EMAIL -> "Verify Your Email";
            case RESET_PASSWORD -> "Reset Password OTP";
            default -> "System Error please contact admin if u are seeing this";
        };

        String message = switch (type) {
            case LOGIN -> "Use the following OTP to log in";
            case VERIFY_EMAIL -> "Use this OTP to verify your email address";
            case RESET_PASSWORD -> "Enter this OTP to reset your password";
            default -> "System Error please contact admin if u are seeing this";
        };

        String finalMessage = String.format("""
        %s: %s

        Note: This OTP will expire in 3 minutes.
        If you did not initiate this request, please ignore this email.
    """, message, otp);

        // Prepare and send the email
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toEmail);
        email.setSubject(subject);
        email.setText(finalMessage);

        //emailSender.send(email);

        return otp;
    }

}
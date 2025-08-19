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
    public void sendLowAttendanceEmail(float att, String email, String subjectName) {
        String subject = "Low Attendance Alert: " + subjectName;
        String message = String.format(
                "Dear Student,\n\nYour attendance in %s is currently %.2f%%. Please ensure that you meet the minimum attendance requirements.\n\nRegards,\nAcademic Office",
                subjectName, att
        );

        System.out.println(message);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom("your_email@gmail.com");  // Optional: override default sender

        mailSender.send(mailMessage);
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

        mailSender.send(email);

        return otp;
    }

}
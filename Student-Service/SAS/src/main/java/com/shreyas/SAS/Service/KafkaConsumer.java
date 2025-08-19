package com.shreyas.SAS.Service;

import com.shreyas.SAS.DTO.LowAttendanceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    @Autowired
    EmailService emailService;
    @KafkaListener(
            topics = "low_attendance_emails",
            groupId = "email-group",
            concurrency = "3"
    )
    public void listen(LowAttendanceDTO obj) {
        emailService.sendLowAttendanceEmail(obj.getAttendance(), obj.getEmail(),obj.getSubject());
    }
}

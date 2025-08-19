package com.shreyas.SAS.Service;

import com.shreyas.SAS.DTO.LowAttendanceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducer {
    private static final String TOPIC_NAME = "low_attendance_emails"; // Define your Kafka topic name

    private final KafkaTemplate<String, LowAttendanceDTO> kafkaTemplate;

    public void sendStudentEvent(LowAttendanceDTO event) {
        kafkaTemplate.send(TOPIC_NAME,event);
    }
}

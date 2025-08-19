package com.shreyas.SAS.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime time;
    boolean isPresent;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonIgnoreProperties("attendances")
    Subject subject;

    public boolean isPresent() {
        return isPresent;
    }

    public Attendance(LocalDateTime time, boolean isPresent, Subject subject) {
        this.time = time;
        this.isPresent = isPresent;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", time=" + time +
                ", isPresent=" + isPresent +
                ", subject=" + subject +
                '}';
    }
}

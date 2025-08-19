package com.shreyas.SAS.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subject {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String name;
        String code;
        @OneToMany(mappedBy = "subject",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
        @JsonIgnore
        List<Attendance> attendances;
        public Subject(String name, String code, List<Attendance> attendances, Student student) {
            this.name = name;
            this.code = code;
            this.attendances = attendances;
            this.student = student;
        }
    @ManyToOne
    @JoinColumn(name = "student_id")
    @JsonIgnore
    Student student;
    public Attendance getParticularAttendance(String dateTime) {
        for(Attendance attendance:attendances) {
            System.out.println(attendance.getTime().toString());
            if(attendance.getTime().toString().equals(dateTime))
                return attendance;
        }
        return null;
    }
    public float calculateAttendance() {
        int p=0,t=0;
        for(Attendance attendance:attendances) {
            if(attendance.isPresent()) {
                p++;
            }
            t++;
        }
        return (float)p/t;
    }
}
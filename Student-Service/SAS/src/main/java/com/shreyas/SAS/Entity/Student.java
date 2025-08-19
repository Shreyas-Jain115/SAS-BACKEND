package com.shreyas.SAS.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shreyas.SAS.Exception.NotFoundException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    @Id
    private Long id; // Same as User.id
    @OneToOne
    @MapsId // Reuse the User's ID
    @JoinColumn(name = "id") // FK and PK are the same
    private User user;
    @ManyToOne()
    @JoinColumn(name = "classroom_id")
    @JsonIgnoreProperties("students")
    Classroom classroom;
    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties("student")
    List<Subject> subjects;

    public Student(User user, Classroom classroom, List<Subject> subjects) {
        this.user=user;
        this.classroom = classroom;
        this.subjects = subjects;
    }
    public Subject getParticularSubject(String code) {
        for(Subject sub:subjects) {
            if(sub.getCode().equals(code))
                return sub;
        }
        return null;
    }
    public List<Attendance> getSubjectAttendance(String code) {
        for(Subject subject:subjects) {
            if(subject.getCode().equals(code)) {
                return subject.getAttendances();
            }
        }
        throw new NotFoundException("Subject Code Not Found");
    }
}

package com.shreyas.SAS.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Classroom {
    @Id
    private Long id; // same as User.id
    @OneToOne
    @MapsId // This tells JPA to use the same ID as in the 'user' entity
    @JoinColumn(name = "id") // Optional, but makes it clearer
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_user_id")
    User adminUser;
    String phoneNo;
    String subjects;
    String subjectCodes;
    @OneToMany(mappedBy = "classroom",fetch = FetchType.LAZY)
    @JsonIgnore
    List<Student> students;

    public Classroom(User user, String phoneNo, String subjects, String subjectCodes,User adminUser, List<Student> students) {
        this.user=user;
        this.phoneNo = phoneNo;
        this.subjects = subjects;
        this.subjectCodes = subjectCodes;
        this.students = students;
        this.adminUser=adminUser;
    }
}

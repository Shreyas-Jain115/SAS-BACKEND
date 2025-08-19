package com.shreyas.SAS.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shreyas.SAS.Entity.Student;
import com.shreyas.SAS.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassroomDTO {
    @Id
    private Long id; // same as User.id
    @OneToOne
    @MapsId // This tells JPA to use the same ID as in the 'user' entity
    @JoinColumn(name = "id") // Optional, but makes it clearer
    private User user;
    String subjects;
    String subjectCodes;

    public ClassroomDTO(User user, String subjects, String subjectCodes) {
        this.user=user;
        this.subjects = subjects;
        this.subjectCodes = subjectCodes;
    }
}

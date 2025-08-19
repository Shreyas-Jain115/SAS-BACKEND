package com.shreyas.SAS.DTO;

import com.shreyas.SAS.Entity.Subject;
import com.shreyas.SAS.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentDTO {
    User user;
    String classroomEmail;
    List<Subject> subjects;

}

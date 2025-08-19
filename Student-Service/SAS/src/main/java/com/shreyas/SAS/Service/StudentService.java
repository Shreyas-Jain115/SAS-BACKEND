package com.shreyas.SAS.Service;

import com.shreyas.SAS.DTO.StudentDTO;
import com.shreyas.SAS.Entity.Attendance;
import com.shreyas.SAS.Entity.Student;
import com.shreyas.SAS.Entity.Subject;
import com.shreyas.SAS.Exception.NotFoundException;
import com.shreyas.SAS.Repo.*;
import com.shreyas.SAS.Security.UserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    AttendanceRepo attendanceRepo;
    public StudentDTO studentLogin() {
        UserPrincipal userDetails =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = Long.parseLong(userDetails.getUsername());
        System.out.println("Student Login"+id);
        Student student= studentRepo.findById(id).orElseThrow();
        System.out.println(student.getUser().getEmail());
        return new StudentDTO(student.getUser(),student.getClassroom().getUser().getEmail(),student.getSubjects());
    }
    @Transactional
    public Boolean markAttendance(String dateTime,String subjectCode) {
        UserPrincipal userDetails =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = Long.parseLong(userDetails.getUsername());
        Student student=studentRepo.findById(id).orElseThrow();
        Subject sub=student.getParticularSubject(subjectCode);
        if(sub==null)
            throw new NotFoundException("Subject Not Found With Name:"+subjectCode);
        Attendance att=sub.getParticularAttendance(dateTime);
        if(att==null)
            throw new NotFoundException("Attendance Not Found AT TIME:"+dateTime);
        att.setPresent(true);
        System.out.println(att.toString());
        attendanceRepo.save(att);
        return true;
    }
    @Transactional
    public List<Attendance> getSubjectAttendance(String subjectCode) {
        UserPrincipal userDetails =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = Long.parseLong(userDetails.getUsername());
        Student student=studentRepo.findById(id).orElseThrow();
        return student.getSubjectAttendance(subjectCode);
    }
}

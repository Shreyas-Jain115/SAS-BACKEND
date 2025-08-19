package com.shreyas.SAS.Controller;

import com.shreyas.SAS.DTO.StudentDTO;
import com.shreyas.SAS.Entity.Attendance;
import com.shreyas.SAS.Service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @GetMapping("/profile")
    @Operation(summary = "getDetails")
    public StudentDTO loginStudent() {
            StudentDTO studentDTO= studentService.studentLogin();
        System.out.println("DONE DANA DONE");
        return studentDTO;
    }
    @PostMapping("/attendance/mark")
    @Operation(summary = "Mark Attendance For Student")
    public Boolean markAttendance(@RequestParam String dateTime ,@RequestParam String subjectCode) {
        return studentService.markAttendance(dateTime,subjectCode);
    }
    @GetMapping("/attendance/subject")
    @Operation(summary = "Gets Attendance of Praticular Subject")
    public List<Attendance> getParticularAttendance(@RequestParam String subjectCode) {
        return studentService.getSubjectAttendance(subjectCode);
    }
}

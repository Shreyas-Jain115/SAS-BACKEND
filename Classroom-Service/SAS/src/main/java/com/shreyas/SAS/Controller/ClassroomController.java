package com.shreyas.SAS.Controller;

import com.shreyas.SAS.DTO.ClassroomDTO;
import com.shreyas.SAS.DTO.StudentAttendanceDTO;
import com.shreyas.SAS.Entity.Classroom;
import com.shreyas.SAS.Entity.User;
import com.shreyas.SAS.Service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {
    @Autowired
    ClassroomService classroomService;
    @GetMapping("/login")
    @Operation(summary = "Login Teacher")
    public ClassroomDTO loginTeacher() {
        return classroomService.classroomLogin();
    }


    @PostMapping("/attendance/start")
    @Operation(summary = "Mark Attendance-Teacher")
    public LocalDateTime markAttendance(@RequestParam String subjectCode) {
        return classroomService.createAttendance(subjectCode  );
    }
    @PostMapping("/student/register/basic")
    @Operation(summary = "Register Student")
    public Boolean registerStudentBasic(@RequestParam Map<String, String> params) {
        System.out.println(params.get("email"));
        return classroomService.registerStudent(params.get("email"));
    }
    @PostMapping("/student/register/otp")
    @Operation(summary = "Register Student")
    public User registerStudentOtp(@RequestParam Map<String, String> params) {
        System.out.println("Params received: " + params);
        return classroomService.registerStudent(params.get("email"), params.get("password"), params.get("otp"));
    }

    @GetMapping("/students/attendance")
    @Operation(summary = "FETCH STUDENT AND ATTENDANCE MAPPED WITH SUBJECT UNDER CLASSROOM")
    public List<StudentAttendanceDTO> getStudentWithAttendance(@RequestParam String code) {
        return classroomService.getStudentWithAttendance(code);
    }
    @PostMapping("/attendance/present")
    @Operation(summary = "Mark Attendance For Student")
    public Boolean markAttendance(@RequestParam Long id) {
        return classroomService.markAttendance(id);
    }
    @PostMapping("/attendance/sendEmail")
    @Operation(summary = "sends email to students")
    public Boolean sendEmail(@RequestBody Map<String, String> body) {
        String subjectCode = body.get("subjectCode");
        return classroomService.sendEmailIfAttLow(subjectCode);
    }

}

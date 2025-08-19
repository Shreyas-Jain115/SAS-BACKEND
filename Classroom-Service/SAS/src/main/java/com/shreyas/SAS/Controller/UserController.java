package com.shreyas.SAS.Controller;

import com.shreyas.SAS.DTO.RequestKey;
import com.shreyas.SAS.DTO.RequestType;
import com.shreyas.SAS.DTO.UserDTO;
import com.shreyas.SAS.Entity.Classroom;
import com.shreyas.SAS.Entity.User;
import com.shreyas.SAS.Repo.UserRepo;
import com.shreyas.SAS.Security.JwtUtil;
import com.shreyas.SAS.Service.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.persistence.PostRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ClassroomService classroomService;
    @Autowired
    UserService userService;


    @PostMapping("/admin/registerClassroom/basic")
    @Operation(summary = "Register Classroom")
    public Boolean registerTeacher(@RequestBody Classroom classroom) {
        return classroomService.registerClassroom(classroom.getUser());
    }
    @PostMapping("/admin/registerClassroom/otp")
    @Operation(summary = "Register Classroom")
    public Classroom registerTeacher(@RequestBody Classroom classroom,@RequestParam String otp) {
        return  classroomService.registerClassroom(classroom.getUser(), classroom.getPhoneNo(), classroom.getSubjects(), classroom.getSubjectCodes(),otp);
    }

    @PostMapping("/login/basic")
    @Operation(summary = "Login")
    public ResponseEntity<Boolean> loginUser1(@RequestBody User user) {
        System.out.println(user.getEmail());
        return userService.loginUser1(user.getEmail(),user.getPassword());
    }
    @PostMapping("/login/otp")
    @Operation(summary = "Login")
    public ResponseEntity<UserDTO> loginUser2(@RequestBody User user,@RequestParam String otp) {
        return userService.loginUser2(user.getEmail(),user.getPassword(),otp);
    }
    @PostMapping("/admin/signUp/basic")
    @Operation(summary = "Admin SignUp")
    public ResponseEntity<Boolean> registerAdmin(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerAdmin1(user));
    }

    @PostMapping("/admin/signUp/otp")
    @Operation(summary = "Admin SignUp")
    public ResponseEntity<User> registerAdmin(@RequestBody User user,@RequestParam String otp) {
        return ResponseEntity.ok(userService.registerAdmin2(user,otp));
    }

    @GetMapping("/admin/classroom")
    public Classroom getClassroom(@RequestParam String email,@RequestParam String password) {
        return userService.classroomLogin(email, password);
    }

    @DeleteMapping("/admin/subject/remove")
    @Operation(summary = "Subject to be removed")
    public Boolean removeSubject(@RequestParam Long id,@RequestParam String subjectCode) {
        System.out.println(id+subjectCode);
        return classroomService.removeSubject(id,subjectCode);
    }
    @PutMapping("/admin/subject/add")
    public Boolean insertSubject(@RequestParam Long id,@RequestParam String subjectName,@RequestParam String subjectCode) {
        return classroomService.addSubject(id,subjectName,subjectCode);
    }

}



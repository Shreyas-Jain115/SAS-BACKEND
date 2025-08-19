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
    UserService userService;

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


}



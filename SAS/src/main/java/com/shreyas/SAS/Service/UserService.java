package com.shreyas.SAS.Service;

import com.shreyas.SAS.DTO.RequestKey;
import com.shreyas.SAS.DTO.RequestType;
import com.shreyas.SAS.DTO.UserDTO;
import com.shreyas.SAS.Entity.Classroom;
import com.shreyas.SAS.Entity.User;
import com.shreyas.SAS.Exception.IncorrectOTP;
import com.shreyas.SAS.Exception.IncorrectPasswordException;
import com.shreyas.SAS.Repo.ClassroomRepo;
import com.shreyas.SAS.Repo.UserRepo;
import com.shreyas.SAS.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    EmailService emailService;
    @Autowired
    RedisService redisService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtUtil jwtUtil;

    public ResponseEntity<Boolean> loginUser1( String email,  String password) {
        User user=userRepo.findByEmail(email);
        System.out.println("ID"+user.getId());
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getId(), password
                )
        );

        emailService.sendOtpEmail(email, RequestType.LOGIN);
        return ResponseEntity.ok(true);
    }

    public ResponseEntity<UserDTO> loginUser2( String email, String password, String otp) {
        User user=userRepo.findByEmail(email);
        System.out.println("ID"+user.getId());
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getId(), password
                )
        );

        String str=redisService.getCommand(new RequestKey(RequestType.LOGIN,email));

        if(str.equals(otp)) {

            String token = jwtUtil.generateToken(String.valueOf(user.getId()), user.getRole());
            System.out.println(token);
            UserDTO userDTO = new UserDTO(token, user.getRole());
            System.out.println(userDTO.toString());
            return ResponseEntity.ok(userDTO);
        }
        throw new IncorrectPasswordException("Incorrect otp");
    }



}

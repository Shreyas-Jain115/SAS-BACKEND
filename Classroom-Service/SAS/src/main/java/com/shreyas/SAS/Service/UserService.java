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
import com.shreyas.SAS.Security.UserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    EmailService emailService;

    @Autowired
    ClassroomService classroomService;
    @Autowired
    RedisService redisService;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ClassroomRepo classroomRepo;

    public Boolean registerAdmin1( User user) {
        emailService.sendOtpEmail(user.getEmail(),RequestType.VERIFY_EMAIL);
        return true;
    }

    public User registerAdmin2( User user,String otp) {
        if((redisService.getCommand(new RequestKey(RequestType.VERIFY_EMAIL,user.getEmail()))).equals(otp)) {
            user.setRole("admin");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return user;
        }
        throw new IncorrectOTP("Incorrect OTP");
    }

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

    public boolean resetPassword1(String emailId) {
        User user=userRepo.findByEmail(emailId);
        if(user!=null) {
            emailService.sendOtpEmail(emailId,RequestType.RESET_PASSWORD);
            return true;
        }
        return false;
    }

    public boolean resetPassword2(String emailId,String otp,String password) {
        User user=userRepo.findByEmail(emailId);
        if(user!=null) {
            String str=redisService.getCommand(new RequestKey(RequestType.RESET_PASSWORD,emailId));
            if(str!=null&&str.equals(otp)) {
                user.setPassword(passwordEncoder.encode(password));
                userRepo.save(user);
                return true;
            }
        }
        return false;
    }
    @Transactional
    public Classroom classroomLogin(String email,String password) {
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id=Long.parseLong(userPrincipal.getUsername());
        User adminUser=userRepo.findById(id).orElseThrow();
        Classroom classroom=classroomRepo.findByUserEmail(email);

        if(classroom.getAdminUser().getEmail().equals(adminUser.getEmail())&&passwordEncoder.matches(password,classroom.getUser().getPassword())) {
            return classroom;
        }
        throw new IncorrectPasswordException("Incorrect Password");
    }

}

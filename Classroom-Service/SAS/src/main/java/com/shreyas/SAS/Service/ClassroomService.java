package com.shreyas.SAS.Service;

import com.shreyas.SAS.DTO.*;
import com.shreyas.SAS.Entity.*;
import com.shreyas.SAS.Exception.CustomException;
import com.shreyas.SAS.Exception.EmailNotFoundExecption;
import com.shreyas.SAS.Exception.IncorrectOTP;
import com.shreyas.SAS.Exception.NotFoundException;
import com.shreyas.SAS.Repo.*;
import com.shreyas.SAS.Security.UserPrincipal;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClassroomService {
    @Autowired
    ClassroomRepo classroomRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    SubjectRepo subjectRepo;
    @Autowired
    AttendanceRepo attendanceRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    RedisService redisService;
    @Autowired
    KafkaProducer kafkaService;
    public Boolean registerStudent(String studentEmail) {
        emailService.sendOtpEmail(studentEmail,RequestType.VERIFY_EMAIL);
        return true;
    }
    public User registerStudent(String studentEmail,String studentPassword,String otp) {
        if((redisService.getCommand(new RequestKey(RequestType.VERIFY_EMAIL,studentEmail))).equals(otp)) {
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long id = Long.parseLong(userPrincipal.getUsername());
            Classroom classroom = classroomRepo.findById(id).orElseThrow();
            List<Subject> subjects = new ArrayList<>();
            User user = new User(studentEmail, passwordEncoder.encode(studentPassword), "student");
            userRepo.save(user);
            Student student = new Student(user, classroom, subjects);
            classroom.getStudents().add(student);
            String[] subject = classroom.getSubjects().split(":");
            String[] subjectCode = classroom.getSubjectCodes().split(":");
            studentRepo.save(student);
            for (int i = 0; i < subject.length; i++) {
                Subject sub = new Subject(subject[i].trim(), subjectCode[i].trim(), new ArrayList<>(), student);
                subjects.add(sub);
                subjectRepo.save(sub);
            }
            student.getUser().setPassword("Why Are U Trying To Access This");
            return student.getUser();
        }
        throw new IncorrectOTP("Incorrect OTP");
    }
    @Transactional
    public Boolean markAttendance(Long id) {
        Attendance att=attendanceRepo.findById(id).orElseThrow();
        att.setPresent(true);
        System.out.println(att.toString());
        attendanceRepo.save(att);
        return true;
    }
    public Boolean registerClassroom(User user) {
        emailService.sendOtpEmail(user.getEmail(), RequestType.VERIFY_EMAIL);
        return true;
    }
    public Classroom registerClassroom(User user, String phoneNo, String subjects, String subjectCodes,String otp) {
        if((redisService.getCommand(new RequestKey(RequestType.VERIFY_EMAIL,user.getEmail()))).equals(otp)) {
            UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long id=Long.parseLong(userPrincipal.getUsername());
            User adminUser=userRepo.findById(id).orElseThrow();
            user.setRole("classroom");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            Classroom classroom = new Classroom(user, phoneNo, subjects, subjectCodes,adminUser, new ArrayList<>());
            classroomRepo.save(classroom);
            return classroom;
        }
        throw new IncorrectOTP("Incorrect OTP");
    }
    public ClassroomDTO classroomLogin() {
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id=Long.parseLong(userPrincipal.getUsername());
        Classroom classroom= classroomRepo.findById(id).orElseThrow();
        ClassroomDTO classroomDTO=new ClassroomDTO(classroom.getUser(),classroom.getSubjects(),classroom.getSubjectCodes());
        return classroomDTO;
    }
    @Transactional
    public LocalDateTime createAttendance(String subjectCode) {
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id=Long.parseLong(userPrincipal.getUsername());
        Classroom classroom = classroomRepo.findById(id).orElseThrow();
        LocalDateTime date=LocalDateTime.now();
        for(Student student: classroom.getStudents()) {
            Subject sub=student.getParticularSubject(subjectCode);
            if(sub!=null) {
                Attendance att = new Attendance(date, false, sub);
                attendanceRepo.save(att);
            }
            else {
                throw new NotFoundException("Subject Not Found With Name:"+subjectCode);
            }
        }
        return date;
    }
    public List<StudentAttendanceDTO> getStudentWithAttendance(String code) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = Long.parseLong(userPrincipal.getUsername());

        return studentRepo.findByClassroomIdAndSubjectsCode(id, code).stream()
                .map(student -> new StudentAttendanceDTO(
                        student.getUser(),
                        attendanceRepo.findBySubjectCodeAndSubjectStudentId(code, student.getId())))
                .toList();
    }
    @Transactional
    public boolean removeSubject(Long id,String subjectCode) {
        Classroom classroom = classroomRepo.findById(id).orElseThrow();
        System.out.println(classroom.getId());
        String[] subjectName=classroom.getSubjects().split(":");
        String[] subjectCodes=classroom.getSubjectCodes().split(":");
        StringBuilder subName=new StringBuilder();
        StringBuilder subCode=new StringBuilder();
        for(int i=0;i<subjectName.length;i++) {
            if(subjectCodes[i].equals(subjectCode)) {
                continue;
            }
            subName.append(subjectName[i]).append(":");
            subCode.append(subjectCodes[i]).append(":");
        }
        if(subCode.length()>0) {
            classroom.setSubjects(subName.substring(0, subName.length() - 1));
            classroom.setSubjectCodes(subCode.substring(0, subCode.length() - 1));
        }
        else {
            classroom.setSubjects("");
            classroom.setSubjectCodes("");
        }
        List<Student> students=classroom.getStudents();
        for(Student student:students) {
            subjectRepo.delete(student.getParticularSubject(subjectCode));
        }
        return true;
    }
    public boolean addSubject(Long id,String subjectName,String subjectCode) {
        Classroom classroom = classroomRepo.findById(id).orElseThrow();
        String subName=classroom.getSubjects()+":"+subjectName;
        String subCode=classroom.getSubjectCodes()+":"+subjectCode;
        classroom.setSubjects(subName);
        classroom.setSubjectCodes(subCode);
        try {
            List<Student> students = classroom.getStudents();
            for (Student student : students) {
                Subject subject = new Subject(subjectName, subjectCode, new ArrayList<>(), student);
                subjectRepo.save(subject);
            }
        }
        catch(Exception e) {
            throw new CustomException("No Students or"+e.getMessage());
        }
        return true;
    }
    @Transactional
    public Boolean sendEmailIfAttLow(String subjectCode) {
        UserPrincipal userPrincipal=(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id=Long.parseLong(userPrincipal.getUsername());
        Classroom classroom = classroomRepo.findById(id).orElseThrow();
        List<Student> students=classroom.getStudents();
        for(Student student:students) {
            Subject subject=student.getParticularSubject(subjectCode);
            float att=subject.calculateAttendance();
            att=100*att;
            if(att<75) {
                System.out.println(student.getUser().getEmail()+":"+att);
                LowAttendanceDTO lowAttendanceDTO=new LowAttendanceDTO(student.getUser().getEmail(),subject.getName(),att);
                kafkaService.sendStudentEvent(lowAttendanceDTO);
            }
        }
        return true;
    }
}

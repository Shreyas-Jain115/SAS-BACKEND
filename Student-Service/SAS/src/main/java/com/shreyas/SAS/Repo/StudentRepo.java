package com.shreyas.SAS.Repo;

import com.shreyas.SAS.DTO.StudentAttendanceDTO;
import com.shreyas.SAS.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student,Long> {
    Student findByUserEmail(String studentEmail);
    List<Student > findByClassroomIdAndSubjectsCode(Long id,String code);
}

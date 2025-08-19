package com.shreyas.SAS.Repo;

import com.shreyas.SAS.Entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepo extends JpaRepository<Attendance,Long> {
    List<Attendance> findBySubjectCodeAndSubjectStudentId(String subjectCode,Long id);
}

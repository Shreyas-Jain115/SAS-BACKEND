package com.shreyas.SAS.Repo;

import com.shreyas.SAS.Entity.Attendance;
import com.shreyas.SAS.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subject,Long> {

}

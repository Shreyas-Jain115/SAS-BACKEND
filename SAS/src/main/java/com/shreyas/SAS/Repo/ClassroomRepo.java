package com.shreyas.SAS.Repo;

import com.shreyas.SAS.Entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepo extends JpaRepository<Classroom,Long> {
    Classroom findByUserEmail(String classroomEmail);
}

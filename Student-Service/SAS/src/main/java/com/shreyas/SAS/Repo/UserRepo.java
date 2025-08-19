package com.shreyas.SAS.Repo;

import com.shreyas.SAS.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {

    User findByEmail(String email);

    boolean existsByEmail(String adminEmail);
}

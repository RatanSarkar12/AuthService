package com.authSecurity.repositories;

import com.authSecurity.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long>{
          Optional<User> findByEmail(String email);
}

package com.authSecurity.repositories;

import com.authSecurity.Model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {

    Optional<Session>findByTokenAndUser_Id(String token,Long userId);
}

package com.example.security.database.repository;

import com.example.security.database.entities.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserOtpRepository extends JpaRepository<UserOtp, Long> {
    Optional<UserOtp> findUserOtpByUserName(String userName);
}

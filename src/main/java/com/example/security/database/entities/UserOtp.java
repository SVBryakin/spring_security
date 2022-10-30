package com.example.security.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "users_otp",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"userName", "otp"})})
public class UserOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String userName;

    private String otp;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}

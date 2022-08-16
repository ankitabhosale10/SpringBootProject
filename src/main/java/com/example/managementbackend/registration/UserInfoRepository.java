package com.example.managementbackend.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByEmail(String email);

    @Query("SELECT U FROM UserInfo U WHERE U.email=:email")
    public UserInfo getByUserEmail(@Param("email") String email);

    @Query("SELECT u FROM UserInfo u WHERE u.verificationCode = ?1")
    public UserInfo findByVerificationCode(String code);

//    UserInfo findByVerificationCode(@Param("authToken") String authToken);
}

package com.example.managementbackend.repository;

import com.example.managementbackend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Object findByEmail(String email);

    @Query("SELECT U FROM UserInfo U WHERE U.email=:email")
    public UserInfo getByUserEmail(@Param("email") String email);
}

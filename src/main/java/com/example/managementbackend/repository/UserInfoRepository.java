package com.example.managementbackend.repository;

import com.example.managementbackend.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

    public Optional<UserInfo> findOneById(Long id);
}

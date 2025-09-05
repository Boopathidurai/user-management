package com.usermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.UserMaster;

@Repository
public interface UserRepo extends JpaRepository<UserMaster,Long> {

    UserMaster findByEmailAndPwd(String email, String pwd);

    UserMaster findByEmail(String email);
}

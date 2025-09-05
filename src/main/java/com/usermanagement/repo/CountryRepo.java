package com.usermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.CountryMaster;

@Repository
public interface CountryRepo extends JpaRepository<CountryMaster,Long> {

}

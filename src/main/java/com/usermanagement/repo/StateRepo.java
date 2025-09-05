package com.usermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.StateMaster;

import java.util.List;

@Repository
public interface StateRepo extends JpaRepository<StateMaster,Long> {

    List<StateMaster> findByCountry_Id(Long countryId);
}

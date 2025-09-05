package com.usermanagement.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagement.entity.CityMaster;

import java.util.List;

@Repository
public interface CityRepo extends JpaRepository<CityMaster,Long> {

    List<CityMaster> findByState_Id(Integer stateId);
}

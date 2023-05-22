package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Long> {


    List<District> findAllByCityId(Integer cityId);
}

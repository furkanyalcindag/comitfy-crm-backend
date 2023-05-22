package com.comitfy.crm.app.repository;

import com.comitfy.crm.app.entity.City;
import com.comitfy.crm.util.common.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City,Long> {


}

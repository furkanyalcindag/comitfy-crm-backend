package com.comitfy.healtie.femaleModules.service;

import com.comitfy.healtie.femaleModules.dto.FertilityLevelDTO;
import com.comitfy.healtie.femaleModules.dto.OvulationCycleDTO;
import com.comitfy.healtie.femaleModules.dto.requestDTO.FertilityLevelRequestDTO;
import com.comitfy.healtie.femaleModules.dto.requestDTO.OvulationCycleRequestDTO;
import com.comitfy.healtie.femaleModules.entity.OvulationCycle;
import com.comitfy.healtie.femaleModules.mapper.FertilityLevelMapper;
import com.comitfy.healtie.femaleModules.mapper.OvulationCycleMapper;
import com.comitfy.healtie.femaleModules.model.enums.FertilityLevelEnum;
import com.comitfy.healtie.femaleModules.repository.FertilityLevelRepository;
import com.comitfy.healtie.femaleModules.repository.OvulationCycleRepository;
import com.comitfy.healtie.femaleModules.repository.PreferenceRepository;
import com.comitfy.healtie.femaleModules.specification.OvulationCycleSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class OvulationCycleService extends BaseService<OvulationCycleDTO, OvulationCycleRequestDTO, OvulationCycle, OvulationCycleRepository, OvulationCycleMapper, OvulationCycleSpecification> {
    @Autowired
    OvulationCycleRepository ovulationCycleRepository;

    @Autowired
    OvulationCycleMapper ovulationCycleMapper;


    @Autowired
    OvulationCycleSpecification ovulationCycleSpecification;

    @Autowired
    UserRepository userRepository;
    @Autowired
    FertilityLevelMapper fertilityLevelMapper;
    @Autowired
    FertilityLevelRepository fertilityLevelRepository;

    @Autowired
    PreferenceRepository preferenceRepository;

    @Override
    public OvulationCycleRepository getRepository() {
        return ovulationCycleRepository;
    }

    @Override
    public OvulationCycleMapper getMapper() {
        return ovulationCycleMapper;
    }

    @Override
    public OvulationCycleSpecification getSpecification() {
        return ovulationCycleSpecification;
    }

    public OvulationCycleRequestDTO saveOvulationCycleByUser(User user, OvulationCycleRequestDTO dto) {

        boolean existId = ovulationCycleRepository.existsByUserAndActivated(user, true);


        if (existId) {

            OvulationCycle ovulationCycle = ovulationCycleRepository.findByActivatedAndUser(true, user);
            ovulationCycle.setActivated(false);


            OvulationCycle ovulationCycle1 = new OvulationCycle();
            ovulationCycle1.setActuality(true);
            ovulationCycle1.setStartingDate(dto.getStartingDate());
            ovulationCycle1.setEstimatedDate(dto.getStartingDate().plus(28, ChronoUnit.DAYS));


            ovulationCycle1.setUser(user);
            ovulationCycle1.setActivated(true);


            ovulationCycleRepository.save(ovulationCycle1);


        } else {
            OvulationCycle ovulationCycle = getMapper().requestDTOToEntity(dto);
            ovulationCycle.setActivated(true);
            ovulationCycle.setActuality(true);
            ovulationCycle.setStartingDate(dto.getStartingDate());
            ovulationCycle.setUser(user);
            ovulationCycleRepository.save(ovulationCycle);

        }
        return dto;
    }

    public ArrayList<FertilityLevelDTO> keepFertilityLevel(OvulationCycleDTO ovulationCycleDTO) {


        ArrayList<FertilityLevelDTO> fertilityLevelDTOS = new ArrayList<>();

        for (int i = 0; i < 28; i++) {
            LocalDate date = ovulationCycleDTO.getStartingDate().plusDays(i);
            if (i < 9 || (i >= 20 && i < 28)) {

                FertilityLevelDTO fertilityLevelDTO = new FertilityLevelDTO();
                fertilityLevelDTO.setDate(date);
                fertilityLevelDTO.setFertilityLevelEnum(FertilityLevelEnum.LOW);
                fertilityLevelDTOS.add(fertilityLevelDTO);
            } else if ((i >= 9 && i < 12) || (i >= 15 && i < 19)) {

                FertilityLevelDTO fertilityLevelDTO = new FertilityLevelDTO();
                fertilityLevelDTO.setDate(date);
                fertilityLevelDTO.setFertilityLevelEnum(FertilityLevelEnum.MIDDLE);
                fertilityLevelDTOS.add(fertilityLevelDTO);
            } else if (i >= 12 && i < 15) {

                FertilityLevelDTO fertilityLevelDTO = new FertilityLevelDTO();
                fertilityLevelDTO.setDate(date);
                fertilityLevelDTO.setFertilityLevelEnum(FertilityLevelEnum.HIGH);
                fertilityLevelDTOS.add(fertilityLevelDTO);
            } else {
                return null;
            }

        }
        return fertilityLevelDTOS;
    }

    public OvulationCycleDTO getActiveOvulationCycle(User user) {

        OvulationCycleDTO ovulationCycleDTO = ovulationCycleMapper.entityToDTO(ovulationCycleRepository.findByActivatedAndUser(true, user));

        List<FertilityLevelDTO> fertilityLevelDTOS = keepFertilityLevel(ovulationCycleDTO);

        for (FertilityLevelDTO fertilityLevelDTO : ovulationCycleDTO.getFertilityLevels()) {

            fertilityLevelDTOS.add(fertilityLevelDTO);

        }
        return ovulationCycleDTO;
    }

}



package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.DoctorDTO;
import com.comitfy.healtie.app.dto.requestDTO.DoctorRequestDTO;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DoctorMapper implements BaseMapper<DoctorDTO, DoctorRequestDTO, Doctor> {
    @Override
    public DoctorDTO entityToDTO(Doctor entity) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setFirstName(entity.getUser().getFirstName());
        doctorDTO.setLastName(entity.getUser().getLastName());
        doctorDTO.setTitle(entity.getTitle());
        doctorDTO.setDiplomaNo(entity.getDiplomaNo());
        doctorDTO.setEmail(entity.getUser().getEmail());
        doctorDTO.setAddress(entity.getAddress());
        doctorDTO.setPhone(entity.getPhone());
        return doctorDTO;

    }

    @Override
    public Doctor dtoToEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.getUser().setFirstName(dto.getFirstName());
        doctor.getUser().setLastName(dto.getLastName());
        doctor.setTitle(dto.getTitle());
        doctor.setDiplomaNo(dto.getDiplomaNo());
        doctor.setAddress(dto.getAddress());
        doctor.getUser().setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        return doctor;

    }

    @Override
    public Doctor requestDTOToEntity(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.getUser().setFirstName(dto.getFirstName());
        doctor.getUser().setLastName(dto.getLastName());
        doctor.setTitle(dto.getTitle());
        doctor.setDiplomaNo(dto.getDiplomaNo());
        doctor.setAddress(dto.getAddress());
        doctor.getUser().setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        return doctor;
    }

    @Override
    public Doctor requestDTOToExistEntity(Doctor doctor, DoctorRequestDTO dto) {
        doctor.getUser().setFirstName(dto.getFirstName());
        doctor.getUser().setLastName(dto.getLastName());
        doctor.setTitle(dto.getTitle());
        doctor.setDiplomaNo(dto.getDiplomaNo());
        doctor.setAddress(dto.getAddress());
        doctor.getUser().setEmail(dto.getEmail());
        doctor.setPhone(dto.getPhone());
        return doctor;
    }

    @Override
    public List<Doctor> dtoListToEntityList(List<DoctorDTO> doctorDTOS) {
        List<Doctor> doctorList = new ArrayList<>();
        for (DoctorDTO doctorDTO : doctorDTOS) {
            Doctor doctor = dtoToEntity(doctorDTO);
            doctorList.add(doctor);
        }
        return doctorList;
    }

    @Override
    public List<DoctorDTO> entityListToDTOList(List<Doctor> doctors) {
        List<DoctorDTO> doctorDTOList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            DoctorDTO doctorDTO = entityToDTO(doctor);
            doctorDTOList.add(doctorDTO);
        }
        return doctorDTOList;
    }

    @Override
    public PageDTO<DoctorDTO> pageEntityToPageDTO(Page<Doctor> pageEntity) {

        PageDTO<DoctorDTO> pageDTO = new PageDTO<DoctorDTO>();
        List<Doctor> entityList = pageEntity.toList();
        List<DoctorDTO> doctorDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, doctorDTOList);

        return pageDTO;
    }
}

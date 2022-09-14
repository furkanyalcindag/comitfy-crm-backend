package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.DoctorDTO;
import com.comitfy.healtie.app.dto.requestDTO.*;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.app.repository.DoctorRepository;
import com.comitfy.healtie.app.service.UserInfoService;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.repository.RoleRepository;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DoctorMapper implements BaseMapper<DoctorDTO, DoctorRequestDTO, Doctor> {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public DoctorDTO entityToDTO(Doctor entity) {
        DoctorDTO doctorDTO = new DoctorDTO();
        doctorDTO.setTitle(entity.getTitle());
        doctorDTO.setDiplomaNo(entity.getDiplomaNo());
        doctorDTO.setAddress(entity.getAddress());
        doctorDTO.setPhone(entity.getPhone());
        doctorDTO.setClinicName(entity.getClinicName());
        doctorDTO.setUuid(entity.getUuid());
        doctorDTO.setBranch(entity.getBranch());
        doctorDTO.setAbout(entity.getAbout());
        doctorDTO.setLanguageEnum(entity.getLanguageEnum());
        doctorDTO.setArticleLikeCount(userInfoService.getLikeCountOfArticleByUser(entity.getUser().getUuid()));
        doctorDTO.setUuid(entity.getUuid());


        doctorDTO.setFirstName(entity.getUser().getFirstName());
        doctorDTO.setLastName((entity.getUser().getLastName()));
        doctorDTO.setEmail(entity.getUser().getEmail());
        doctorDTO.setGenderEnum(entity.getUser().getGenderEnum());
        doctorDTO.setAgeRangeEnum(entity.getUser().getAgeRangeEnum());
        doctorDTO.setProfileImage(entity.getUser().getPhotoLink());
        doctorDTO.setUserName(entity.getUser().getEmail());


  /*      if (entity.getArticleList() != null) {
            doctorDTO.setArticleCount(entity.getArticleList().size());
        }*/

        return doctorDTO;
    }

    @Override
    public Doctor dtoToEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setTitle(dto.getTitle());
        doctor.setDiplomaNo(dto.getDiplomaNo());
        doctor.setAddress(dto.getAddress());
        doctor.setPhone(dto.getPhone());
        doctor.setBranch(dto.getBranch());
        doctor.setAbout(dto.getAbout());
        doctor.setClinicName(dto.getClinicName());
        doctor.setLanguageEnum(dto.getLanguageEnum());

        return doctor;

    }

    @Override
    public Doctor requestDTOToEntity(DoctorRequestDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setTitle(dto.getTitle());
        doctor.setDiplomaNo(dto.getDiplomaNo());
        doctor.setAddress(dto.getAddress());
        doctor.setPhone(dto.getPhone());
        doctor.setBranch(dto.getBranch());
        doctor.setAbout(dto.getAbout());
        doctor.setClinicName(dto.getClinicName());
        doctor.setLanguageEnum(dto.getLanguageEnum());

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setGenderEnum(dto.getGenderEnum());
        user.setAgeRangeEnum(dto.getAgeRangeEnum());
        user.setPhotoLink(dto.getPhotoLink());
        user.setUsername(dto.getEmail());

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("doctor").get());
        user.setRoles(roles);

        doctor.setUser(user);


        return doctor;
    }


    @Override
    public Doctor requestDTOToExistEntity(Doctor doctor, DoctorRequestDTO dto) {
        doctor.setTitle(dto.getTitle());
        doctor.setDiplomaNo(dto.getDiplomaNo());
        doctor.setAddress(dto.getAddress());
        doctor.setPhone(dto.getPhone());
        doctor.setBranch(dto.getBranch());
        doctor.setAbout(dto.getAbout());
        doctor.setClinicName(dto.getClinicName());
        doctor.setLanguageEnum(dto.getLanguageEnum());


        doctor.getUser().setFirstName(dto.getFirstName());
        doctor.getUser().setLastName(dto.getLastName());
        doctor.getUser().setEmail(dto.getEmail());
        doctor.getUser().setPassword(passwordEncoder.encode(dto.getPassword()));
        doctor.getUser().setGenderEnum(dto.getGenderEnum());
        doctor.getUser().setAgeRangeEnum(dto.getAgeRangeEnum());
        doctor.getUser().setPhotoLink(dto.getPhotoLink());


        return doctor;
    }

    public Doctor requestDTOToExistEntityForTitle(Doctor doctor, DoctorTitleRequestDTO dto) {
        doctor.setTitle(dto.getTitle());
        return doctor;

    }

    public Doctor requestDTOToExistEntityForProfileImage(Doctor doctor, DoctorProfileImageRequestDTO dto) {
        doctor.setProfileImage(dto.getProfileImage());
        return doctor;

    }

    public Doctor requestDTOToExistEntityForDiplomaNo(Doctor doctor, DoctorDiplomaNoRequestDTO dto) {
        doctor.setDiplomaNo(dto.getDiplomaNo());
        return doctor;

    }

    public Doctor requestDTOToExistEntityForAddress(Doctor doctor, DoctorAddressRequestDTO dto) {
        doctor.setAddress(dto.getAddress());
        return doctor;

    }

    public Doctor requestDTOToExistEntityForPhone(Doctor doctor, DoctorPhoneRequestDTO dto) {
      //  doctor.setUuid(dto.getUuid());
        doctor.setPhone(dto.getPhone());
        return doctor;

    }

    public Doctor requestDTOToExistEntityForClinicName(Doctor doctor, DoctorClinicNameRequestDTO dto) {
        doctor.setClinicName(dto.getClinicName());
        return doctor;

    }

    public Doctor requestDTOToExistEntityForAbout(Doctor doctor, DoctorAboutRequestDTO dto) {
        doctor.setAbout(dto.getAbout());
        return doctor;

    }

    public Doctor requestDTOToExistEntityForBranch(Doctor doctor, DoctorBranchRequestDTO dto) {
        doctor.setBranch(dto.getBranch());
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

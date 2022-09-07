package com.comitfy.healtie.app.mapper;

import com.comitfy.healtie.app.dto.AcademicInfoDTO;
import com.comitfy.healtie.app.dto.requestDTO.AcademicInfoRequestDTO;
import com.comitfy.healtie.app.entity.AcademicInfo;
import com.comitfy.healtie.app.entity.Doctor;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

@Component
public class AcademicInfoMapper implements BaseMapper<AcademicInfoDTO, AcademicInfoRequestDTO, AcademicInfo> {
    @Override
    public AcademicInfoDTO entityToDTO(AcademicInfo entity) {
        AcademicInfoDTO academicInfoDTO=new AcademicInfoDTO();
        academicInfoDTO.setSchoolName(entity.getSchoolName());
        academicInfoDTO.setStartYear(entity.getStartYear());
        academicInfoDTO.setGraduateYear(entity.getGraduateYear());
        academicInfoDTO.setProfession(entity.getProfession());
        academicInfoDTO.setUuid(entity.getUuid());


        return  academicInfoDTO;
    }

    @Override
    public AcademicInfo dtoToEntity(AcademicInfoDTO dto) {
        AcademicInfo academicInfo=new AcademicInfo();
        academicInfo.setSchoolName(dto.getSchoolName());
        academicInfo.setStartYear(dto.getStartYear());
        academicInfo.setGraduateYear(dto.getGraduateYear());
        academicInfo.setProfession(dto.getProfession());


        return academicInfo;
    }

    @Override
    public AcademicInfo requestDTOToEntity(AcademicInfoRequestDTO dto) {
        AcademicInfo academicInfo=new AcademicInfo();
        academicInfo.setSchoolName(dto.getSchoolName());
        academicInfo.setStartYear(dto.getStartYear());
        academicInfo.setGraduateYear(dto.getGraduateYear());
        academicInfo.setProfession(dto.getProfession());
        academicInfo.setUuid(dto.getUuid());

        return academicInfo;

    }

    @Override
    public AcademicInfo requestDTOToExistEntity(AcademicInfo academicInfo, AcademicInfoRequestDTO dto) {
        academicInfo.setSchoolName(dto.getSchoolName());
        academicInfo.setStartYear(dto.getStartYear());
        academicInfo.setGraduateYear(dto.getGraduateYear());
        academicInfo.setProfession(dto.getProfession());

        return academicInfo;
    }

    @Override
    public List<AcademicInfo> dtoListToEntityList(List<AcademicInfoDTO> academicInfoDTOS) {
        List<AcademicInfo> academicInfoList=new ArrayList<>();
        for (AcademicInfoDTO academicInfoDTO: academicInfoDTOS){
            AcademicInfo academicInfo=dtoToEntity(academicInfoDTO);
            academicInfoList.add(academicInfo);
        }return academicInfoList;
    }

    @Override
    public List<AcademicInfoDTO> entityListToDTOList(List<AcademicInfo> academicInfos) {
        List<AcademicInfoDTO> academicInfoDTOList=new ArrayList<>();
        for (AcademicInfo academicInfo:academicInfos){
            AcademicInfoDTO academicInfoDTO=entityToDTO(academicInfo);
            academicInfoDTOList.add(academicInfoDTO);
        }return academicInfoDTOList;
    }

    @Override
    public PageDTO<AcademicInfoDTO> pageEntityToPageDTO(Page<AcademicInfo> pageEntity) {
        PageDTO<AcademicInfoDTO> pageDTO = new PageDTO<AcademicInfoDTO>();
        List<AcademicInfo> entityList = pageEntity.toList();
        List<AcademicInfoDTO> academicInfoDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, academicInfoDTOList);

        return pageDTO;
    }
}

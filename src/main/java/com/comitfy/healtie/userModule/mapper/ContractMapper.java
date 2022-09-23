package com.comitfy.healtie.userModule.mapper;

import com.comitfy.healtie.app.dto.CategoryDTOForArticle;
import com.comitfy.healtie.app.dto.ContractActiveDTO;
import com.comitfy.healtie.app.entity.Category;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.dto.ContractDTO;
import com.comitfy.healtie.userModule.dto.RoleDTO;
import com.comitfy.healtie.userModule.dto.UserContractDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.ContractRequestDTO;
import com.comitfy.healtie.userModule.entity.Contract;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.repository.RoleRepository;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ContractMapper implements BaseMapper<ContractDTO, ContractRequestDTO, Contract> {

    @Autowired
    RoleRepository roleRepository;
    @Override
    public ContractDTO entityToDTO(Contract entity) {
        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setKey(entity.getKey());
        contractDTO.setTitle(entity.getTitle());
        contractDTO.setContent(entity.getContent());
        contractDTO.setActive(entity.getActivated());
        contractDTO.setRequired(entity.getRequired());
        contractDTO.setUuid(entity.getUuid());
        contractDTO.setLanguageEnum(entity.getLanguageEnum());

        Set<RoleDTO> roleDTOS=new HashSet<>();
        for (Role role:entity.getRoleList()){
            RoleDTO roleDTO=new RoleDTO();
            roleDTO.setName(role.getName());
            roleDTO.setUuid(role.getUuid());
            roleDTOS.add(roleDTO);
        }

        contractDTO.setRoleSet(roleDTOS);
        return contractDTO;


    }

    public UserContractDTO entityToDTOUserContract(Contract entity) {

        UserContractDTO userContractDTO = new UserContractDTO();
        userContractDTO.setUuid(entity.getUuid());
        userContractDTO.setContractUuid(entity.getUuid());

        return userContractDTO;

    }

    public ContractActiveDTO entityToDTOActive(Contract entity) {
        ContractActiveDTO contractDTO = new ContractActiveDTO();
        contractDTO.setKey(entity.getKey());
        contractDTO.setTitle(entity.getTitle());
        contractDTO.setRequired(entity.getRequired());
        contractDTO.setActive(entity.getActivated());
        contractDTO.setUuid(entity.getUuid());
        contractDTO.setLanguageEnum(entity.getLanguageEnum());

        Set<RoleDTO> roleDTOS=new HashSet<>();
        for (Role role:entity.getRoleList()){
            RoleDTO roleDTO=new RoleDTO();
            roleDTO.setName(role.getName());
            roleDTO.setUuid(role.getUuid());
            roleDTOS.add(roleDTO);
        }
        contractDTO.setRoleSet(roleDTOS);

        return contractDTO;

    }

    @Override
    public Contract dtoToEntity(ContractDTO dto) {
        Contract contract = new Contract();
        contract.setKey(dto.getKey());
        contract.setTitle(dto.getTitle());
        contract.setContent(dto.getContent());
        contract.setActivated(dto.isActive());
        contract.setRequired(dto.isRequired());
        contract.setLanguageEnum(LanguageEnum.valueOf(dto.getLanguage()));
        return contract;
    }


    @Override
    public Contract requestDTOToEntity(ContractRequestDTO dto) {
        Contract contract = new Contract();
        contract.setKey(dto.getKey());
        contract.setTitle(dto.getTitle());
        contract.setContent(dto.getContent());
        contract.setActivated(dto.isActive());
        contract.setRequired(dto.isRequired());
        contract.setLanguageEnum(LanguageEnum.valueOf(dto.getLanguage()));

        contract.setRoleList(new HashSet<>());
        for (UUID uuid:dto.getRoleList()){
            Optional<Role> role=roleRepository.findByUuid(uuid);
            role.ifPresent(value->contract.getRoleList().add(value));
        }

        return contract;
    }

    @Override
    public Contract requestDTOToExistEntity(Contract contract, ContractRequestDTO dto) {

        contract.setKey(dto.getKey());
        contract.setTitle(dto.getTitle());
        contract.setContent(dto.getContent());
        contract.setActivated(dto.isActive());
        contract.setRequired(dto.isRequired());
        contract.setLanguageEnum(LanguageEnum.valueOf(dto.getLanguage()));

        return contract;
    }

    @Override
    public List<Contract> dtoListToEntityList(List<ContractDTO> contractDTOS) {
        List<Contract> contractList = new ArrayList<>();
        for (ContractDTO contractDTO : contractDTOS) {
            Contract contract = dtoToEntity(contractDTO);
            contractList.add(contract);
        }
        return contractList;
    }

    @Override
    public List<ContractDTO> entityListToDTOList(List<Contract> contracts) {
        List<ContractDTO> contractDTOList = new ArrayList<>();
        for (Contract contract : contracts) {
            ContractDTO contractDTO = entityToDTO(contract);
            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    public List<ContractActiveDTO> entityListToDTOListActive(List<Contract> contracts) {
        List<ContractActiveDTO> contractDTOList = new ArrayList<>();
        for (Contract contract : contracts) {
            ContractActiveDTO contractDTO = entityToDTOActive(contract);
            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    public List<UserContractDTO> entityListToDTOListUserContract(List<Contract> contracts) {
        List<UserContractDTO> contractDTOList = new ArrayList<>();
        for (Contract contract : contracts) {
            UserContractDTO contractDTO = entityToDTOUserContract(contract);
            contractDTOList.add(contractDTO);
        }
        return contractDTOList;
    }

    @Override
    public PageDTO<ContractDTO> pageEntityToPageDTO(Page<Contract> pageEntity) {
        PageDTO<ContractDTO> pageDTO = new PageDTO<ContractDTO>();
        List<Contract> entityList = pageEntity.toList();
        List<ContractDTO> contractDTOList = entityListToDTOList(entityList);
        pageDTO.setStart(pageEntity, contractDTOList);
        return pageDTO;
    }

    public PageDTO<ContractActiveDTO> pageActiveEntityToPageDTO(Page<Contract> pageEntity) {
        PageDTO<ContractActiveDTO> pageDTO = new PageDTO<ContractActiveDTO>();
        List<Contract> entityList = pageEntity.toList();
        List<ContractActiveDTO> contractActiveDTOList = entityListToDTOListActive(entityList);
        pageDTO.setStart(pageEntity, contractActiveDTOList);
        return pageDTO;
    }

    public PageDTO<UserContractDTO> pageEntityToPageDTOUserContract(Page<Contract> pageEntity) {
        PageDTO<UserContractDTO> pageDTO = new PageDTO<UserContractDTO>();
        List<Contract> entityList = pageEntity.toList();
        List<UserContractDTO> contractDTOList = entityListToDTOListUserContract(entityList);
        pageDTO.setStart(pageEntity, contractDTOList);
        return pageDTO;
    }

}

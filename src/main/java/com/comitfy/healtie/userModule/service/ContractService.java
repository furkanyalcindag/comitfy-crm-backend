package com.comitfy.healtie.userModule.service;

import com.comitfy.healtie.app.dto.ContractActiveDTO;
import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.dto.requestDTO.TagRequestDTO;
import com.comitfy.healtie.app.entity.Tag;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.dto.ContractDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.ContractRequestDTO;
import com.comitfy.healtie.userModule.entity.Contract;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.mapper.ContractMapper;
import com.comitfy.healtie.userModule.repository.ContractRepository;
import com.comitfy.healtie.userModule.repository.RoleRepository;
import com.comitfy.healtie.userModule.repository.UserRepository;
import com.comitfy.healtie.userModule.specification.ContractSpecification;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ContractService extends BaseService<ContractDTO, ContractRequestDTO, Contract, ContractRepository, ContractMapper, ContractSpecification> {

    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ContractMapper contractMapper;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ContractSpecification contractSpecification;

    @Autowired
    UserRepository userRepository;

    @Override
    public ContractRepository getRepository() {
        return contractRepository;
    }

    @Override
    public ContractMapper getMapper() {
        return contractMapper;
    }

    @Override
    public ContractSpecification getSpecification() {
        return contractSpecification;
    }

    public ContractRequestDTO saveContractByUser(UUID id, ContractRequestDTO dto, LanguageEnum languageEnum) {
        Optional<User> user = userRepository.findByUuid(id);
        if (user.isPresent()) {
            Contract contract = getMapper().requestDTOToEntity(dto);
            contract.setLanguageEnum(languageEnum);
            contractRepository.save(contract);
            return dto;
        } else {
            return null;
        }
    }

    public PageDTO<ContractActiveDTO> getActiveContract(int page, int size, LanguageEnum languageEnum) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        PageDTO<ContractActiveDTO> pageDTO = contractMapper.pageActiveEntityToPageDTO(contractRepository.findAllByActivatedAndLanguageEnum(pageable, true, languageEnum));

        return pageDTO;

    }

    public PageDTO<ContractActiveDTO> getActiveContractByRole(UUID id, int page, int size,LanguageEnum languageEnum) {
        Optional<Role> role = roleRepository.findByUuid(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        if (role.isPresent()) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role.get());
            return contractMapper.pageActiveEntityToPageDTO(contractRepository.findAllByRoleListInAndActivated(pageable, roleSet, true));
        } else {
            return null;
        }
    }

    public ContractDTO updateContract(UUID id, ContractRequestDTO dto, User user) {
        Optional<Contract> contract = contractRepository.findByUuid(id);
        if (contract.isPresent()) {
            Contract contract1=contractMapper.requestDTOToExistEntity(contract.get(),dto);
            contract1.setKey(dto.getKey());
            contract1.setTitle(dto.getTitle());
            contract1.setContent(dto.getContent());
            contract1.setActivated(dto.isActive());
            contract1.setRequired(dto.isRequired());
            contractRepository.save(contract1);

            return contractMapper.entityToDTO(contract1);
        } else {
            return null;
        }

    }

}

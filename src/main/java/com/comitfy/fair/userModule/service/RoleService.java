package com.comitfy.fair.userModule.service;

import com.comitfy.fair.userModule.dto.RoleDTO;
import com.comitfy.fair.userModule.dto.requestDTO.RoleRequestDTO;
import com.comitfy.fair.userModule.entity.Role;
import com.comitfy.fair.userModule.mapper.RoleMapper;
import com.comitfy.fair.userModule.repository.RoleRepository;
import com.comitfy.fair.userModule.specification.RoleSpecification;
import com.comitfy.fair.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<RoleDTO, RoleRequestDTO, Role, RoleRepository, RoleMapper, RoleSpecification> {


    @Autowired
    RoleRepository repository;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RoleSpecification roleSpecification;

    @Override
    public RoleRepository getRepository() {
        return repository;
    }

    @Override
    public RoleMapper getMapper() {
        return roleMapper;
    }

    @Override
    public RoleSpecification getSpecification() {
        return roleSpecification;
    }


}

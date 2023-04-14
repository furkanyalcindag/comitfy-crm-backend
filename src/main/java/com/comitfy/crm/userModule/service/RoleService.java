package com.comitfy.crm.userModule.service;

import com.comitfy.crm.userModule.dto.RoleDTO;
import com.comitfy.crm.userModule.dto.requestDTO.RoleRequestDTO;
import com.comitfy.crm.userModule.entity.Role;
import com.comitfy.crm.userModule.mapper.RoleMapper;
import com.comitfy.crm.userModule.repository.RoleRepository;
import com.comitfy.crm.userModule.specification.RoleSpecification;
import com.comitfy.crm.util.common.BaseService;
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

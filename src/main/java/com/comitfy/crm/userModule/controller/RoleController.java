package com.comitfy.crm.userModule.controller;

import com.comitfy.crm.userModule.dto.RoleDTO;
import com.comitfy.crm.userModule.dto.requestDTO.RoleRequestDTO;
import com.comitfy.crm.userModule.entity.Role;
import com.comitfy.crm.userModule.mapper.RoleMapper;
import com.comitfy.crm.userModule.repository.RoleRepository;
import com.comitfy.crm.userModule.service.RoleService;
import com.comitfy.crm.userModule.specification.RoleSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("user-api/role")
public class RoleController extends BaseCrudController<RoleDTO, RoleRequestDTO, Role, RoleRepository, RoleMapper, RoleSpecification, RoleService> {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMapper roleMapper;


    @Override
    protected RoleService getService() {
        return roleService;
    }

    @Override
    protected RoleMapper getMapper() {
        return roleMapper;
    }
}

package com.comitfy.fair.userModule.controller;

import com.comitfy.fair.userModule.dto.RoleDTO;
import com.comitfy.fair.userModule.dto.requestDTO.RoleRequestDTO;
import com.comitfy.fair.userModule.entity.Role;
import com.comitfy.fair.userModule.mapper.RoleMapper;
import com.comitfy.fair.userModule.repository.RoleRepository;
import com.comitfy.fair.userModule.service.RoleService;
import com.comitfy.fair.userModule.specification.RoleSpecification;
import com.comitfy.fair.util.common.BaseCrudController;
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

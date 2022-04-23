package com.comitfy.healtie.userModule.controller;

import com.comitfy.healtie.userModule.dto.RoleDTO;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.userModule.mapper.RoleMapper;
import com.comitfy.healtie.userModule.repository.RoleRepository;
import com.comitfy.healtie.userModule.service.RoleService;
import com.comitfy.healtie.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController extends BaseCrudController<RoleDTO, Role, RoleRepository, RoleMapper, RoleService> {

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

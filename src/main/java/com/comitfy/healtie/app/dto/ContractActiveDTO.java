package com.comitfy.healtie.app.dto;

import com.comitfy.healtie.userModule.dto.RoleDTO;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ContractActiveDTO extends BaseDTO {
    private String key;

    private String title;

    private boolean isRequired;

    private boolean isActive;
    private Set<RoleDTO> roleSet;
}

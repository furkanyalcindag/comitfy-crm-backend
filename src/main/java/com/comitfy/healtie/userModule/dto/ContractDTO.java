package com.comitfy.healtie.userModule.dto;

import com.comitfy.healtie.app.dto.CategoryDTOForArticle;
import com.comitfy.healtie.userModule.entity.Role;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.util.Set;

@Data
public class ContractDTO extends BaseDTO {

    private String key;

    private String title;

    private String content;

    private boolean isRequired;

    private boolean isActive;

    private Set<RoleDTO> roleSet;

}

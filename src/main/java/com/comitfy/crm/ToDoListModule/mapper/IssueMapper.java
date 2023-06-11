package com.comitfy.crm.ToDoListModule.mapper;

import com.comitfy.crm.ToDoListModule.dto.BoardColumnDTO;
import com.comitfy.crm.ToDoListModule.dto.IssueDTO;
import com.comitfy.crm.ToDoListModule.dto.requestDTO.BoardColumnRequestDTO;
import com.comitfy.crm.ToDoListModule.dto.requestDTO.IssueRequestDTO;
import com.comitfy.crm.ToDoListModule.entity.BoardColumn;
import com.comitfy.crm.ToDoListModule.entity.Issue;
import com.comitfy.crm.ToDoListModule.service.BoardColumnService;
import com.comitfy.crm.app.dto.AutoCompleteDTO;
import com.comitfy.crm.app.dto.ProductDTO;
import com.comitfy.crm.app.dto.ProposalProductDTO;
import com.comitfy.crm.app.entity.Product;
import com.comitfy.crm.app.entity.ProposalProduct;
import com.comitfy.crm.app.service.ProductService;
import com.comitfy.crm.app.service.ProposalService;
import com.comitfy.crm.userModule.entity.User;
import com.comitfy.crm.userModule.service.UserService;
import com.comitfy.crm.util.common.BaseMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IssueMapper extends BaseMapper<IssueDTO, IssueRequestDTO, Issue> {


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "uuid", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "updatedDate", ignore = true),
            @Mapping(target = "createdBy", ignore = true),
            @Mapping(target = "lastModifiedBy", ignore = true)
    })
    @Override
    void update(@MappingTarget Issue entity, Issue updateEntity);


    @Mappings({
            //@Mapping(target = "proposalUUID", expression = "java(setProposalUUID(entity,proposalService))"),

            @Mapping(target = "user", expression = "java(setUser(entity,userService))"),

            //@Mapping(target = "materialDTO", expression = "java(setMaterial(entity,materialService))")
    })
    IssueDTO entityToDTONew(Issue entity,
                                      @Context UserService userService);



    @Mappings({
            @Mapping(source = "uuid", target = "value"),

            @Mapping(source = "name", target = "label")
    })
    AutoCompleteDTO entityToAutoCompleteDTO(Issue entity);

    default Issue requestDTOToEntity(IssueRequestDTO dto, @Context UserService userService, @Context BoardColumnService boardColumnService){

        Issue issue = new Issue();
        issue.setName(dto.getName());
        issue.setDescription(dto.getDescription());
        issue.setPriority(dto.getPriority());
        issue.setBoardColumnId(boardColumnService.findEntityByUUID(dto.boardColumnUUID).getId());
        issue.setTags(dto.getTags());

        if(dto.getUserUUID()!=null){
            issue.setUserId(userService.findEntityByUUID(dto.getUserUUID()).getId());
        }


        return issue;
    }



    default String setUser(Issue entity, UserService userService) {


        User user = userService.findEntityById(entity.getUserId());



        return user.getFirstName()+" "+user.getLastName();

    }


}

package com.comitfy.crm.ToDoListModule.service;

import com.comitfy.crm.ToDoListModule.dto.IssueDTO;
import com.comitfy.crm.ToDoListModule.dto.requestDTO.IssueRequestDTO;
import com.comitfy.crm.ToDoListModule.entity.Issue;
import com.comitfy.crm.ToDoListModule.mapper.IssueMapper;
import com.comitfy.crm.ToDoListModule.repository.IssueRepository;
import com.comitfy.crm.ToDoListModule.specification.IssueSpecification;
import com.comitfy.crm.userModule.service.UserService;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class IssueService extends BaseService<IssueDTO, IssueRequestDTO, Issue, IssueRepository, IssueMapper, IssueSpecification> {

    @Autowired
    IssueMapper issueMapper;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    IssueSpecification issueSpecification;

    @Autowired
    UserService userService;

    @Autowired
    BoardColumnService boardColumnService;

    @Override
    public IssueRepository getRepository() {
        return issueRepository;
    }

    @Override
    public IssueMapper getMapper() {
        return issueMapper;
    }

    @Override
    public IssueSpecification getSpecification() {
        return issueSpecification;
    }


    public IssueDTO save(IssueRequestDTO requestDTO) {
        Issue entity = getMapper().requestDTOToEntity(requestDTO,userService,boardColumnService);
        getRepository().save(entity);
        return getMapper().entityToDTONew(entity,userService);
    }



    public IssueDTO updateColumn(UUID issueUUID,IssueRequestDTO requestDTO) {
        Issue entity =findEntityByUUID(issueUUID);
        entity.setBoardColumnId(boardColumnService.findEntityByUUID(requestDTO.getBoardColumnUUID()).getId());
        getRepository().save(entity);
        return getMapper().entityToDTONew(entity,userService);
    }


    public IssueDTO updateUser(UUID issueUUID,IssueRequestDTO requestDTO) {
        Issue entity =findEntityByUUID(issueUUID);
        entity.setUserId(userService.findEntityByUUID(requestDTO.getUserUUID()).getId());
        getRepository().save(entity);
        return getMapper().entityToDTONew(entity,userService);
    }


    public IssueDTO update(UUID id, IssueRequestDTO dto) {
        Optional<Issue> entity = getRepository().findByUuid(id);

        if (entity.isPresent()) {
            Issue entity1 = getMapper().requestDTOToEntity(dto,userService,boardColumnService);
            getMapper().update(entity.get(), entity1);
            getRepository().save(entity.get());
            return getMapper().entityToDTONew(entity.get(),userService);
        } else {
            return null;
        }
    }


}

package com.comitfy.crm.ToDoListModule.controller;

import com.comitfy.crm.ToDoListModule.dto.BoardColumnDTO;
import com.comitfy.crm.ToDoListModule.dto.requestDTO.BoardColumnRequestDTO;
import com.comitfy.crm.ToDoListModule.entity.BoardColumn;
import com.comitfy.crm.ToDoListModule.mapper.BoardColumnMapper;
import com.comitfy.crm.ToDoListModule.repository.BoardColumnRepository;
import com.comitfy.crm.ToDoListModule.service.BoardColumnService;
import com.comitfy.crm.ToDoListModule.specification.BoardColumnSpecification;
import com.comitfy.crm.util.common.BaseCrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kanban/board-column")
public class BoardColumnController extends BaseCrudController<BoardColumnDTO, BoardColumnRequestDTO, BoardColumn, BoardColumnRepository, BoardColumnMapper, BoardColumnSpecification, BoardColumnService> {

    @Autowired
    BoardColumnService boardColumnService;

    @Autowired
    BoardColumnMapper boardColumnMapper;

    @Override
    protected BoardColumnService getService() {
        return boardColumnService;
    }

    @Override
    protected BoardColumnMapper getMapper() {
        return boardColumnMapper;
    }
}

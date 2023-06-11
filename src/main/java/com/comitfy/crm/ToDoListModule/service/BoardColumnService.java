package com.comitfy.crm.ToDoListModule.service;

import com.comitfy.crm.ToDoListModule.dto.BoardColumnDTO;
import com.comitfy.crm.ToDoListModule.dto.requestDTO.BoardColumnRequestDTO;
import com.comitfy.crm.ToDoListModule.entity.BoardColumn;
import com.comitfy.crm.ToDoListModule.mapper.BoardColumnMapper;
import com.comitfy.crm.ToDoListModule.repository.BoardColumnRepository;
import com.comitfy.crm.ToDoListModule.specification.BoardColumnSpecification;
import com.comitfy.crm.util.common.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardColumnService extends BaseService<BoardColumnDTO, BoardColumnRequestDTO, BoardColumn, BoardColumnRepository, BoardColumnMapper, BoardColumnSpecification> {

    @Autowired
    BoardColumnMapper boardColumnMapper;

    @Autowired
    BoardColumnRepository boardColumnRepository;

    @Autowired
    BoardColumnSpecification boardColumnSpecification;


    @Override
    public BoardColumnRepository getRepository() {
        return boardColumnRepository;
    }

    @Override
    public BoardColumnMapper getMapper() {
        return boardColumnMapper;
    }

    @Override
    public BoardColumnSpecification getSpecification() {
        return boardColumnSpecification;
    }
}

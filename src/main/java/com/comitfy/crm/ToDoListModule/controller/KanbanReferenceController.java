package com.comitfy.crm.ToDoListModule.controller;

import com.comitfy.crm.ToDoListModule.model.enums.PriorityDTO;
import com.comitfy.crm.ToDoListModule.model.enums.PriorityEnum;
import com.comitfy.crm.ToDoListModule.service.KanbanReferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("kanban/reference")
public class KanbanReferenceController {

    @Autowired
    KanbanReferenceService referenceService;


    @GetMapping("get-priority")
    public ResponseEntity<List<PriorityDTO>> getProposalStatuses() {

        return new ResponseEntity<>(referenceService.getPriorityEnum(), HttpStatus.OK);

    }




}

package com.comitfy.crm.ToDoListModule.service;

import com.comitfy.crm.ToDoListModule.model.enums.PriorityDTO;
import com.comitfy.crm.ToDoListModule.model.enums.PriorityEnum;
import com.comitfy.crm.app.dto.CurrencyDTO;
import com.comitfy.crm.app.entity.City;
import com.comitfy.crm.app.entity.District;
import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import com.comitfy.crm.app.model.enums.ProposalStatusEnum;
import com.comitfy.crm.app.repository.CityRepository;
import com.comitfy.crm.app.repository.DistrictRepository;
import com.comitfy.crm.app.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KanbanReferenceService {


    public List<PriorityDTO> getPriorityEnum() {

        List<PriorityDTO> priorityDTOList = new ArrayList<>();

        List<PriorityEnum> list = Stream.of(PriorityEnum.values())

                .collect(Collectors.toList());

        for (PriorityEnum priorityEnum: list) {

            PriorityDTO priorityDTO = new PriorityDTO();
            priorityDTO = priorityEnum.toDTO();
            priorityDTOList.add(priorityDTO);

        }


        //list.remove(ProposalStatusEnum.APPROVED);

        return priorityDTOList;

    }



}

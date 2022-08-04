package com.comitfy.healtie.commercial.service;

import com.comitfy.healtie.commercial.dto.HighlightsDTO;
import com.comitfy.healtie.commercial.dto.request.HighlightsRequestDTO;
import com.comitfy.healtie.commercial.entity.Highlights;
import com.comitfy.healtie.commercial.entity.Order;
import com.comitfy.healtie.commercial.mapper.HighlightsMapper;
import com.comitfy.healtie.commercial.repository.HighlightsRepository;
import com.comitfy.healtie.commercial.repository.OrderRepository;
import com.comitfy.healtie.commercial.specification.HighlightsSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

@Service
public class HighlightsService extends BaseService<HighlightsDTO, HighlightsRequestDTO, Highlights, HighlightsRepository, HighlightsMapper, HighlightsSpecification> {


    Logger log = LoggerFactory.getLogger(HighlightsService.class);
    @Autowired
    HighlightsRepository highlightsRepository;
    @Autowired
    HighlightsMapper highlightsMapper;
    @Autowired
    HighlightsSpecification highlightsSpecification;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public HighlightsRepository getRepository() {
        return highlightsRepository;
    }

    @Override
    public HighlightsMapper getMapper() {
        return highlightsMapper;
    }

    @Override
    public HighlightsSpecification getSpecification() {
        return highlightsSpecification;
    }


    public HighlightsRequestDTO saveHighlightsByOrder(UUID orderUUID, HighlightsRequestDTO dto, User user) {
        Optional<Order> order = orderRepository.findByUuid(orderUUID);
        if (order.isPresent()) {
            Highlights highlights = getMapper().requestDTOToEntity(dto);
            highlights.setUserUUID(user.getUuid());
            highlights.setOrderUUID(orderUUID);

            highlightsRepository.save(highlights);
            return dto;
        } else return null;
    }


    public PageDTO<HighlightsDTO> getActiveHighlightsOnCurrentDate(int page,int size){


        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));

        LocalDateTime currentDateTime = LocalDateTime.now();
        log.info("getActiveHighlightsOnCurrentDate : {}", currentDateTime);

        return getMapper().pageEntityToPageDTO(highlightsRepository.getActivatedHighlightsOfDoctorsOnCurrentDate(pageable, currentDateTime));

    }




}

package com.comitfy.healtie.commercial.controller;

import com.comitfy.healtie.app.dto.ContractActiveDTO;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.commercial.dto.HighlightsDTO;
import com.comitfy.healtie.commercial.dto.request.HighlightsRequestDTO;
import com.comitfy.healtie.commercial.entity.Highlights;
import com.comitfy.healtie.commercial.entity.Order;
import com.comitfy.healtie.commercial.mapper.HighlightsMapper;
import com.comitfy.healtie.commercial.repository.HighlightsRepository;
import com.comitfy.healtie.commercial.service.HighlightsService;
import com.comitfy.healtie.commercial.service.OrderService;
import com.comitfy.healtie.commercial.specification.HighlightsSpecification;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("highlights")
public class HighlightsController extends BaseCrudController<HighlightsDTO, HighlightsRequestDTO, Highlights, HighlightsRepository, HighlightsMapper, HighlightsSpecification, HighlightsService> {
    @Autowired
    HighlightsService highlightsService;
    @Autowired
    HighlightsMapper highlightsMapper;

    @Autowired
    HelperService helperService;

    @Autowired
    OrderService orderService;

    @Override
    protected HighlightsService getService() {
        return highlightsService;
    }

    @Override
    protected HighlightsMapper getMapper() {
        return highlightsMapper;
    }

    @PostMapping("/{orderId}")
    public ResponseEntity<HighlightsRequestDTO> saveHighlightsByProduct(@PathVariable UUID orderId, @RequestBody HighlightsRequestDTO dto) {

        User user = helperService.getUserFromSession();
        Order order = orderService.findEntityByUUID(orderId);
        if (order != null) {
            return new ResponseEntity<>(highlightsService.saveHighlightsByOrder(orderId, dto, user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/active-highlights/")
    public ResponseEntity<PageDTO<HighlightsDTO>> getActiveHighlights(@RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<HighlightsDTO> dtoList = highlightsService.getActiveHighlightsOnCurrentDate(pageNumber, pageSize);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }


}

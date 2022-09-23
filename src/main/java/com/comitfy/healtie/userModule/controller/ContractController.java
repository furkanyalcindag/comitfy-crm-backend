package com.comitfy.healtie.userModule.controller;

import com.comitfy.healtie.app.dto.ContractActiveDTO;
import com.comitfy.healtie.app.dto.TagDTO;
import com.comitfy.healtie.app.dto.requestDTO.TagRequestDTO;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.userModule.dto.ContractDTO;
import com.comitfy.healtie.userModule.dto.requestDTO.ContractRequestDTO;
import com.comitfy.healtie.userModule.entity.Contract;
import com.comitfy.healtie.userModule.entity.User;
import com.comitfy.healtie.userModule.mapper.ContractMapper;
import com.comitfy.healtie.userModule.repository.ContractRepository;
import com.comitfy.healtie.userModule.service.ContractService;
import com.comitfy.healtie.userModule.specification.ContractSpecification;
import com.comitfy.healtie.util.PageDTO;
import com.comitfy.healtie.util.common.BaseCrudController;
import com.comitfy.healtie.util.common.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("contract")
public class ContractController extends BaseCrudController<ContractDTO, ContractRequestDTO, Contract, ContractRepository, ContractMapper, ContractSpecification, ContractService> {
    @Autowired
    ContractService contractService;
    @Autowired
    ContractMapper contractMapper;

    @Autowired
    HelperService helperService;

    @Override
    protected ContractService getService() {
        return contractService;
    }

    @Override
    protected ContractMapper getMapper() {
        return contractMapper;
    }

    @PostMapping("/user-api")
    public ResponseEntity<ContractRequestDTO> saveByUserId(@RequestHeader(value = "accept-language", required = true) String language,
                                                           @RequestBody ContractRequestDTO contractRequestDTO) {
        User user = helperService.getUserFromSession();
        if (user != null) {
            {
                return new ResponseEntity<>(contractService.saveContractByUser(user.getUuid(), contractRequestDTO, LanguageEnum.valueOf(language)), HttpStatus.OK);
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/active-contract/")
    public ResponseEntity<PageDTO<ContractActiveDTO>> getActiveContract(@RequestHeader(value = "accept-language", required = true) String language,
                                                                        @RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<ContractActiveDTO> dtoList = contractService.getActiveContract(pageNumber, pageSize, LanguageEnum.valueOf(language));
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/active-contracts-by-role/{roleId}")
    public ResponseEntity<PageDTO<ContractActiveDTO>> getActiveContractByRole(@RequestHeader(value = "accept-language", required = true) String language,
                                                                              @PathVariable UUID roleId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        PageDTO<ContractActiveDTO> pageDTO = contractService.getActiveContractByRole(roleId, pageNumber, pageSize, LanguageEnum.valueOf(language));
        return new ResponseEntity<>(pageDTO, HttpStatus.OK);
    }

    @PutMapping("/user-api/{contractId}")
    public ResponseEntity<String> updateContract(@PathVariable UUID contractId, @RequestBody ContractRequestDTO dto) {
        User user = helperService.getUserFromSession();
        ContractDTO contractDTO=contractService.findByUUID(contractId);

        if (contractDTO == null || user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND);
        } else {
           // dto.setLanguageEnum(LanguageEnum.valueOf(contractDTO.getLanguage()));
            contractService.updateContract(contractId, dto, user);
            return new ResponseEntity<>("The object was updated.", HttpStatus.OK);
        }

    }
}

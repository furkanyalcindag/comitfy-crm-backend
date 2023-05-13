package com.comitfy.crm.app.dto;

import com.comitfy.crm.util.common.BaseDTO;
import lombok.Data;


@Data
public class CustomerDTO extends BaseDTO {

    private String companyName;
    private String taxNumber;
    private String taxOffice;
    private String telephoneNumber;
    private String address;
    private String iban;
    private String authorizedPerson;

}

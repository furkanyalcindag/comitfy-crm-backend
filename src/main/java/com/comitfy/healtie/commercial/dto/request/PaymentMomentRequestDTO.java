package com.comitfy.healtie.commercial.dto.request;

import com.comitfy.healtie.commercial.model.enums.CurrencyEnum;
import com.comitfy.healtie.commercial.model.enums.PaymentTypeEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

@Data
public class PaymentMomentRequestDTO extends BaseDTO {

    private PaymentTypeEnum paymentTypeEnum;

    private Float paidAmount;

    private CurrencyEnum currencyEnum;

}

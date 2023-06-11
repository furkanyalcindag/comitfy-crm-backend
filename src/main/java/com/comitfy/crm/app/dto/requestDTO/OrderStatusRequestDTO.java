package com.comitfy.crm.app.dto.requestDTO;

import com.comitfy.crm.app.model.enums.OrderStatusEnum;
import lombok.Data;

@Data
public class OrderStatusRequestDTO {

    private OrderStatusEnum orderStatusEnum;

}

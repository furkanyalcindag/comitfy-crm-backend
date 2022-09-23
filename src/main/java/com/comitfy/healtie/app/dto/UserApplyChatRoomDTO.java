package com.comitfy.healtie.app.dto;

import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.util.UUID;

@Data
public class UserApplyChatRoomDTO extends BaseDTO {
    private UUID userUUID;
    private UUID chatRoomUUID;
    private boolean approved;
}

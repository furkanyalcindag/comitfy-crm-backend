package com.comitfy.healtie.app.dto;

import com.comitfy.healtie.app.model.enums.ChatRoomStatusEnum;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ChatRoomDTO extends BaseDTO {

    private String name;

    private int userLimit;

    private String colorCode;

  //  private Boolean approved;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private UUID doctorUUID;

    private LanguageEnum languageEnum;

    private ChatRoomStatusEnum chatRoomStatusEnum;

    private Long messageCount;

}

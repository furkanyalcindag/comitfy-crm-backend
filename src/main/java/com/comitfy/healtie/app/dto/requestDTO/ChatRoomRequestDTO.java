package com.comitfy.healtie.app.dto.requestDTO;

import com.comitfy.healtie.app.model.enums.ChatRoomStatusEnum;
import com.comitfy.healtie.app.model.enums.LanguageEnum;
import com.comitfy.healtie.util.common.BaseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Data
public class ChatRoomRequestDTO extends BaseDTO {

    private String name;

    private int userLimit;

    private String colorCode;

    //private Boolean approved;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private UUID doctorUUID;

    @JsonIgnore
    private LanguageEnum languageEnum;


    private ChatRoomStatusEnum chatRoomStatusEnum;


}

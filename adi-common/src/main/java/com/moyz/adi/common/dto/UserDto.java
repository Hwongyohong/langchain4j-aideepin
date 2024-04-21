package com.moyz.adi.common.dto;


import com.moyz.adi.common.enums.UserStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserDto {

    private String uuid;

    private String name;

    private UserStatusEnum userStatus;

    private LocalDateTime activeTime;

    private Integer userLevel;
}

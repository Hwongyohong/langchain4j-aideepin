package com.moyz.adi.common.dto;


import com.moyz.adi.common.enums.UserStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class UserResp {

    private String name;

    private String uuid;

    private Integer quotaByTokenDaily;

    private Integer quotaByTokenMonthly;

    private Integer quotaByRequestDaily;

    private Integer quotaByRequestMonthly;

    private Integer quotaByImageDaily;

    private Integer quotaByImageMonthly;

    private UserStatusEnum userStatus;

    private Integer userLevel;
}

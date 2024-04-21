package com.moyz.adi.common.dto;

import com.moyz.adi.common.enums.AiModelStatus;
import lombok.Data;

@Data
public class ModelReq
{
    private String name;

    private String remark;

    private AiModelStatus modelStatus;

    private String company;

    private Integer level;
}

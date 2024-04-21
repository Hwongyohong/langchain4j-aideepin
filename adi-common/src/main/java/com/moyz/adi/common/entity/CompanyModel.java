package com.moyz.adi.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.moyz.adi.common.enums.AiModelStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("adi_user_model")
@Schema(title = "用户模型列表", description = "AI模型表")
public class CompanyModel
{

    @Schema(title = "模型名称")
    @TableField("name")
    private String name;

    @Schema(title = "说明")
    @TableField("remark")
    private String remark;

    @Schema(title = "状态(1:正常使用,2:不可用)")
    @TableField("model_status")
    private AiModelStatus modelStatus;

    @Schema(title = "所属公司")
    @TableField("company")
    private String company;

    @Schema(title = "级别")
    @TableField("level")
    private Integer level;
}

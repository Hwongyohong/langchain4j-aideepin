package com.moyz.adi.chat.controller;

import com.moyz.adi.common.base.ThreadContext;
import com.moyz.adi.common.entity.User;
import com.moyz.adi.common.helper.ImageModelContext;
import com.moyz.adi.common.helper.LLMContext;
import com.moyz.adi.common.vo.ImageModelInfo;
import com.moyz.adi.common.vo.LLMModelInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/model")
public class ModelController {
    @Operation(summary = "支持的大语言模型列表")
    @GetMapping(value = "/llms")
    public List<LLMModelInfo> llms()
    {
        User user = ThreadContext.getCurrentUser();
        List<LLMModelInfo> llmModelInfos = new ArrayList<>();
        if (user == null) {
            return llmModelInfos;
        } else {
            // 获取当前用户的等级
            int userLevel = user.getUserLevel();
            llmModelInfos = LLMContext.NAME_TO_MODEL.values().stream().filter(item -> userLevel == 1 || Objects.equals(item.getLevel(), userLevel))
                    .collect(Collectors.toList());
            return llmModelInfos;
        }

    }

    @Operation(summary = "支持的图片模型列表")
    @GetMapping(value = "/imageModels")
    public List<ImageModelInfo> imageModels() {
        return ImageModelContext.NAME_TO_MODEL.values().stream().collect(Collectors.toList());
    }
}

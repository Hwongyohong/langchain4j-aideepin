package com.moyz.adi.chat.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyz.adi.common.base.ThreadContext;
import com.moyz.adi.common.dto.*;
import com.moyz.adi.common.searchengine.SearchEngineContext;
import com.moyz.adi.common.service.AdminService;
import com.moyz.adi.common.service.UserModelService;
import com.moyz.adi.common.service.UserService;
import com.moyz.adi.common.vo.SearchEngineInfo;
import com.ramostear.captcha.HappyCaptcha;
import com.ramostear.captcha.support.CaptchaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@Slf4j
@Tag(name = "权限controller", description = "权限controller")
@Validated
@RestController
@RequestMapping("admin")
public class AdminController
{


    @Resource
    private AdminService adminService;


    @Operation(summary = "搜索用户列表")
    @GetMapping(value = "/users/search")
    public Page<UserDto> searchUsers(String keyword, @NotNull @Min(1) Integer currentPage, @NotNull @Min(10) Integer pageSize)
    {
        Page<UserDto> page =(adminService.searchUsers(keyword, currentPage, pageSize));
        return adminService.searchUsers(keyword, currentPage, pageSize);
    }

    @Operation(summary = "保存用户")
    @PostMapping(value = "/users/save")
    public boolean saveUser(@RequestBody UserReq userReq) {
        return adminService.saveUser(userReq);
    }

    @Operation(summary = "编辑用户")
    @PostMapping(value = "/users/edit/{id}")
    public UserResp editUser(@PathVariable String id) {
        return adminService.editUser(id);
    }




    @Operation(summary = "编辑模型")
    @PostMapping(value = "/models/edit/{name}")
    public ModelResp editModel(@PathVariable String name) {
        return adminService.editModel(name);
    }
    @Operation(summary = "搜索模型列表")
    @GetMapping(value = "/models/search")
    public Page<ModelDto> searchModels(String keyword, @NotNull @Min(1) Integer currentPage, @NotNull @Min(10) Integer pageSize) {
        return adminService.searchModels(keyword, currentPage, pageSize);
    }
    @Operation(summary = "保存模型")
    @PostMapping(value = "/models/save")
    public boolean saveModel(@RequestBody ModelReq modelReq) {
        return adminService.saveModel(modelReq);
    }

    @Operation(summary = "更新模型")
    @PostMapping(value = "/models/update")
    public boolean updateModel(@RequestBody ModelReq modelReq) {
        return adminService.updateModel(modelReq);
    }

    @Operation(summary = "删除模型")
    @PostMapping(value = "/models/del/{name}")
    public boolean delModel(@PathVariable String name) {
        return adminService.delModel(name);
    }
}

package com.moyz.adi.common.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.moyz.adi.common.dto.*;
import com.moyz.adi.common.entity.CompanyModel;
import com.moyz.adi.common.entity.User;
import com.moyz.adi.common.enums.AiModelStatus;
import com.moyz.adi.common.util.MPPageUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author moyz
 * @since 2023-04-11
 */
@Slf4j
@Service
public class AdminService {


    @Resource
    private UserService userService;

    @Resource
    private UserModelService userModelService;



    /**
     *
     * @param userName 用户名
     * @param currentPage 页码
     * @param pageSize 页数
     * @return 分页
     */
    public Page<UserDto> searchUsers(String userName, Integer currentPage, Integer pageSize)
    {
        Page<User> userPage;
        if (StringUtils.isNotBlank(userName)) {
            userPage = userService.lambdaQuery()
                    .like(User::getName,userName)
                    .orderByDesc(User::getCreateTime)
                    .page(new Page<>(currentPage, pageSize));
        } else {
            userPage = userService.lambdaQuery()
                    .orderByDesc(User::getCreateTime)
                    .page(new Page<>(currentPage, pageSize));
        }
        return MPPageUtil.convertToPage(userPage, new Page<>(), UserDto.class);
    }

    /**
     *
     * @param userReq 请求对象
     * @return 更新
     */
    public boolean saveUser(UserReq userReq)
    {
        User user =  userService.lambdaQuery().eq(User::getUuid,userReq.getUuid()).one();
        BeanUtils.copyProperties(userReq,user);
        userService.updateById(user);
        return  userService.updateById(user);
    }

    /**
     *
     * @param uuid 唯一
     * @return 对象
     */
    public UserResp editUser(String uuid)
    {
        User user =  userService.getByUuid(uuid);
        UserResp resp = new UserResp();
        BeanUtils.copyProperties(user,resp);
        return resp;
    }

    /**
     *
     * @param name 唯一
     * @return 对象
     */
    public ModelResp editModel(String name)
    {
        ModelResp resp = new ModelResp();
        CompanyModel companyModel =  userModelService.lambdaQuery().eq(CompanyModel::getName,name).one();
        BeanUtils.copyProperties(companyModel,resp);
        return resp;
    }

    /**
     *
     * @param keyword  名字
     * @param currentPage 页码
     * @param pageSize 页数
     * @return 分页     */
    public Page<ModelDto> searchModels(String keyword, Integer currentPage, Integer pageSize)
    {
        Page<CompanyModel> modelsPage;
        if (StringUtils.isNotBlank(keyword)) {
            modelsPage = userModelService.lambdaQuery()
                    .like(CompanyModel::getName, keyword)
                    .page(new Page<>(currentPage, pageSize));
        } else {
            modelsPage = userModelService.lambdaQuery()
                    .page(new Page<>(currentPage, pageSize));
        }
        return MPPageUtil.convertToPage(modelsPage, new Page<>(), ModelDto.class);
    }

    /**
     *
     * @param modelReq 对象
     * @return 更新
     */
    public boolean saveModel(ModelReq modelReq)
    {
        CompanyModel companyModel =  userModelService.lambdaQuery().eq(CompanyModel::getName,modelReq.getName()).one();
        BeanUtils.copyProperties(modelReq,companyModel);
            return  userModelService.saveOrUpdate(companyModel,new LambdaQueryWrapper<CompanyModel>().eq(CompanyModel::getName,modelReq.getName()));
    }

    /**
     *
     * @param name 唯一
     * @return 对象
     */
    public boolean delModel(String name)
    {
        CompanyModel companyModel =  userModelService.lambdaQuery().eq(CompanyModel::getName,name).one();
        companyModel.setModelStatus(AiModelStatus.INACTIVE);
        return  userModelService.updateById(companyModel);
    }

    public boolean updateModel(ModelReq modelReq) {
        CompanyModel companyModel =  userModelService.lambdaQuery().eq(CompanyModel::getName,modelReq.getName()).one();
        BeanUtils.copyProperties(modelReq,companyModel);
        return  userModelService.updateById(companyModel);
    }
}

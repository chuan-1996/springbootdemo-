package com.example.bootdemo.controller;

import com.example.bootdemo.common.HAStatusCheck;
import com.example.bootdemo.dao.UserDao;
import com.example.bootdemo.entity.UserEntity;
import com.example.bootdemo.utils.RedisUtil;
import com.example.bootdemo.service.SessionService;
import com.example.bootdemo.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author qq491
 * test1为原始请求
 */
@Controller
@RequestMapping("/aop")
public class AopTestController {

    @Autowired
    public SessionService sessionService;

    @RequestMapping(value = "/test1", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultVo test1() {
        return new ResultVo(1,"success",sessionService.listData());
    }

    /**
     * 方法2,3为shiro的AOP权限管理
     */
    @RequiresPermissions("user:admin")
    @RequestMapping(value = "/test2", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test2() {
        return new ResultVo(1,"success",sessionService.listData());
    }

    @RequiresRoles("管理员")
    @RequestMapping(value = "/test3", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test3() {
        return new ResultVo(1,"success",sessionService.listData());
    }

    /**
     * 此接口接入了自定义切面
     */
    @HAStatusCheck
    @RequestMapping(value = "/test4", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo test4() {
        return new ResultVo(1,"success",sessionService.listData());
    }




}

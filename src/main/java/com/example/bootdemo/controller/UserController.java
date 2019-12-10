package com.example.bootdemo.controller;

import com.example.bootdemo.vo.UserSimple;
import com.example.bootdemo.vo.UserVO;
import com.example.bootdemo.service.BeanMapperService;
import com.example.bootdemo.service.UserService;
import com.example.bootdemo.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chuan
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;
    @Autowired
    public BeanMapperService beanMapperService;

    /**
     * 显示全部用户
     */
    @RequiresPermissions("user:admin")
    @RequestMapping(value = "/show",method = RequestMethod.GET)
    @ResponseBody
    public ResultVo hello(){

        return new ResultVo(1,"success",userService.getUsers());
    }

    /**
     用户注册
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo userAdd(@RequestParam("username") String userName,
                            @RequestParam("password") String password) {

        return userService.saveUsers(userName,password);
    }

    /**
     获取当前用户的用户名和头像
     */
    @RequestMapping(value = "/whoami", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo user() {

        return new ResultVo(1,"success",beanMapperService.mapper(userService.getUser(), UserSimple.class));
    }

    /**
     删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo userDelete(HttpServletRequest request) {

        userService.deleteUser(userService.getUser());
        return new ResultVo(1,"success",null);
    }

    /**
     获取当前用户的详细信息
     */
    @RequestMapping(value = "/authc/whoami", method = RequestMethod.GET)
    @ResponseBody
    public  ResultVo userAllInfo(){

        return new ResultVo(1,"success",beanMapperService.mapper(userService.getUser(), UserVO.class));
    }

    /**
     * 修改密码
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo changePassword(@RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("password1") String password1,
                                   @RequestParam("password2") String password2) {

        return userService.updatePassword(oldPassword, password1, password2);
    }

    /**
     * 修改信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo changeUserInfo(@RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("password1") String password1,
                                   @RequestParam("password2") String password2) {

        return userService.updatePassword(oldPassword, password1, password2);
    }
}

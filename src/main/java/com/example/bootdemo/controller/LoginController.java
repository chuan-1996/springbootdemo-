package com.example.bootdemo.controller;

import com.example.bootdemo.entity.User;
import com.example.bootdemo.vo.UserSimple;
import com.example.bootdemo.service.BeanMapperService;
import com.example.bootdemo.service.UserService;
import com.example.bootdemo.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author qq491
 */
@Controller
public class LoginController {

    @Autowired
    public UserService userService;
    @Autowired
    public BeanMapperService beanMapperService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResultVo login(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("remember") String remember,
                          HttpServletRequest request) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        token.setRememberMe(Boolean.parseBoolean(remember));
        subject.login(token);
        if (subject.isAuthenticated()) {
            User a = userService.getUser();
            logger.info(subject.getSession().getHost()+" SessionID:"+subject.getSession().getId()+" 用户"+a.getId()+"登录了");
            return new ResultVo(1,"登录成功",beanMapperService.mapper(a, UserSimple.class));
        } else {
            token.clear();
            return new ResultVo(0,"登录失败",null);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo login2() {

        return new ResultVo(0,"请先登录",null);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ResultVo logout() {

        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultVo(1,"logout success",null);
    }
}

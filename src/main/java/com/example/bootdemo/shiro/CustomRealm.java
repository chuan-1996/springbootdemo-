package com.example.bootdemo.shiro;

import com.example.bootdemo.entity.User;
import com.example.bootdemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.HashSet;
import java.util.Set;

/**
 * @author qq491
 */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    public UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(CustomRealm.class);
    /**
     * 用户密码认证器
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("---------------------------->身份认证中");
        String username = (String) authenticationToken.getPrincipal();
        User a = userService.getUserByUserName(username);
        String password = a.getPasswd();
        return new SimpleAuthenticationInfo(username, password,ByteSource.Util.bytes(a.getSalt()),getName());
    }

    /**
     * 用户权限检查器(当访问有权限需求的API时触发)
     * 权限管理的两种方式，一种是判断角色Role，另一种是判断权限Permissions
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User a = userService.getUserByUserName(username);
        String role = a.getRole();
        logger.info(role+username+"尝试访问非普通用户权限接口");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        info.addRole(role);
        Set<String> stringSet = new HashSet<>();
        if("管理员".equals(role)){
            stringSet.add("user:admin");
        }
        info.setStringPermissions(stringSet);
        return info;
    }
}

package com.example.bootdemo.service;

import com.example.bootdemo.utils.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.bootdemo.dao.*;
import com.example.bootdemo.entity.*;
import com.example.bootdemo.vo.*;

import java.util.List;

/**
 * @author chuan
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    BeanMapperService beanMapperService;

    //根据用户名返回一个User
    public User getUserByUserName(String username) {
        return userMapper.getUserByUserName(username);
    }

    //返回当前用户名
    private String getUserName() {
        return (String) SecurityUtils.getSubject().getPrincipal();
    }

    //返回当前用户
    public User getUser(){return getUserByUserName(getUserName());}

    //删除用户
    public void deleteUser(User a) {
        userMapper.deleteByPrimaryKey(a.getId());
    }

    //返回所有用户
    public List<User> getUsers() {
        UserExample userExample = new UserExample();
        List<User> userList = userMapper.selectByExample(userExample);
        return userList;
    }

    //注册
    public ResultVo saveUsers(String name,String password) {
        User user1 = getUserByUserName(name);
        if (isEmpty.isObjectNotEmpty(user1)) {
            return new ResultVo(0,"该用户已经存在",null);
        }
        User user = new User();
        user.setUsername(name);
        user.setRole("用户");
        List<User> a = getUsers();
        user.setId(a.get(a.size() - 1).getId()+1);
        String salt = PasswordUtil.generateSalt();
        user.setSalt(salt);
        user.setPasswd(PasswordUtil.encryptPassword(password,salt));
        user.setImg(new SecureRandomNumberGenerator().nextBytes().toHex());
        userMapper.insertSelective(user);
        String path="E:/root/image/"+salt+".png";
        return new ResultVo(1,"success",beanMapperService.mapper(user,UserVO.class));
    }

    //修改密码
    public ResultVo updatePassword(String password,String password1,String password2) {
        User a = getUser();
        String oldPassword = PasswordUtil.encryptPassword(password, a.getSalt());
        String newPassword = PasswordUtil.encryptPassword(password1, a.getSalt());
        if ("".equals(password1)) {
            return new ResultVo(0,"新密码不能为空",null);
        }
        if (!password1.equals(password2)) {
            return new ResultVo(0,"新密码两次输入不相同",null);
        }
        if (!oldPassword.equals(a.getPasswd())) {
            return new ResultVo(0,"原密码输入错误",null);
        }
        if (newPassword.equals(a.getPasswd())) {
            return new ResultVo(0,"新密码不能和原密码相同",null);
        }
        a.setPasswd(newPassword);
        userMapper.updateByPrimaryKey(a);
        return new ResultVo(1,"success",null);
    }


}

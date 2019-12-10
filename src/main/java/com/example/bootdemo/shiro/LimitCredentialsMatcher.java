package com.example.bootdemo.shiro;

import com.example.bootdemo.utils.RedisUtil;
import jdk.nashorn.internal.runtime.regexp.joni.constants.TargetInfo;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qq491
 */
@Component
public class LimitCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
    public RedisUtil a;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        Object retryCount2 = a.redisTemplate.opsForValue().get(username);
        int retryCount = retryCount2==null?0:(int)retryCount2;
        int limitCount = 5;
        if ( retryCount>= limitCount) {
            //返回冻结账户名
            throw new ExcessiveAttemptsException(username + "被锁定");
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (!matches) {
            a.redisTemplate.opsForValue().increment(username);
            a.redisTemplate.expire(username,120, TimeUnit.SECONDS);
            int leave = limitCount - retryCount -1;
            throw new IncorrectCredentialsException(String.valueOf(leave));
        }
        if (matches) {
            //clear retry data
            a.redisTemplate.opsForValue().set(username,0);
        }
        return matches;
    }

}

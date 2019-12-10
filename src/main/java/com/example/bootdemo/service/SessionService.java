package com.example.bootdemo.service;

import org.apache.shiro.session.Session;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author qq491
 */
@Service
public class SessionService {

    @Autowired
    private RedisSessionDAO sessionDAO;

    /**
     *  获取活跃session个数
     */
    public int count() {
        return sessionDAO.getActiveSessions().size();
    }

    /**
     *  获取活跃session
     */
    public  Collection<Session> listData() {
        return  sessionDAO.getActiveSessions();
    }

    /**
     *  踢出session
     */
    public Boolean delete(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        sessionDAO.delete(session);
        return true;
    }
}

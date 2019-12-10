package com.example.bootdemo.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qq491
 */
public class ShiroSessionListener implements SessionListener{

    /**
     * 服务器session缓存中的数量不准确代表实际数量
     */
    private static final  AtomicInteger SESSION_COUNT = new AtomicInteger(0);
    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);

    /**
     * 会话创建时触发
     */
    @Override
    public void onStart(Session session) {
        logger.info(session.getHost() + "建立会话 "+session.getId());
        System.out.println("Session:" + SESSION_COUNT.incrementAndGet());
    }

    /**
     * 退出会话时触发
     */
    @Override
    public void onStop(Session session) {
        logger.info(session.getHost() + "会话主动登出 "+session.getId());
        System.out.println("Session：" +SESSION_COUNT.decrementAndGet());
    }

    /**
     * 会话过期时触发
     */
    @Override
    public void onExpiration(Session session) {
        logger.info(session.getHost() + "会话过期 "+session.getId());
        System.out.println("Session：" + SESSION_COUNT.decrementAndGet());
    }
    /**
     * 获取session数量
     */
    public  int getSessionCount() {
        return SESSION_COUNT.get();
    }
}

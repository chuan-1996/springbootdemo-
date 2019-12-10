package com.example.bootdemo.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//使用方法：
//    两种切入方式
//    注解切入方式： @annotation定义切入点为被自定义注解的方法
//    Pattern切入方式：
//    1）execution(* *(..))
//    表示匹配所有方法
//    2）execution(public * com. savage.service.UserService.*(..))
//    表示匹配com.savage.server.UserService中所有的公有方法
//    3）execution(* com.savage.server..*.*(..))
//    表示匹配com.savage.server包及其子包下的所有方法
//
//    使用表达式控制切入的点
//    @Pointcut("execution(* com.savage.aop.MessageSender.*(..))")
//    private void logSender(){}
//
//    @Pointcut("execution(* com.savage.aop.MessageReceiver.*(..))")
//    private void logReceiver(){}
//
//    @Pointcut("logSender() || logReceiver()")
//    private void logMessage(){}

/**
 * @author qq491
 */
@Aspect
@Component
public class TestAspect {
    private Logger logger = LoggerFactory.getLogger(TestAspect.class);

    /**
     * @Description: 定义切入点
     */
    @Pointcut("@annotation(com.example.bootdemo.common.HAStatusCheck)")
    //@Pointcut("execution(public * com.example.bootdemo.controller.UserController.*(..))")
    public void pointCut(){
        System.out.println("切入点pointCut织入！");
    }

    /**
     * @Description: 定义前置通知
     */
    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        // 接收到请求，记录请求内容
        logger.info("【注解：Before】------------------切面  before");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        logger.info("【注解：Before】浏览器输入的网址=URL : " + request.getRequestURL().toString());
        logger.info("【注解：Before】HTTP_METHOD : " + request.getMethod());
        logger.info("【注解：Before】IP : " + request.getRemoteAddr());
        logger.info("【注解：Before】执行的业务方法名=CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("【注解：Before】业务方法获得的参数=ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * @Description: 后置返回通知
     */
    @AfterReturning(returning = "ret", pointcut = "pointCut()")
    public void afterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("【注解：AfterReturning】这个会在切面最后的最后打印，方法的返回值 : " + ret);
    }

    /**
     * @Description: 后置异常通知
     */
    @AfterThrowing("pointCut()")
    public void afterThrowing(JoinPoint jp){
        logger.info("【注解：AfterThrowing】方法异常时执行.....");
    }

    /**
     * @Description: 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行
     */
    @After("pointCut()")
    public void after(JoinPoint jp){
        logger.info("【注解：After】方法最后执行.....");
    }

    /**
     * @Description: 环绕通知,环绕增强，相当于MethodInterceptor
     */
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint pjp) {
        logger.info("【注解：Around . 环绕前】方法环绕start.....");
        try {
            //如果不执行这句，会不执行切面的Before方法及controller的业务方法
            Object o =  pjp.proceed();
            logger.info("【注解：Around. 环绕后】方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}

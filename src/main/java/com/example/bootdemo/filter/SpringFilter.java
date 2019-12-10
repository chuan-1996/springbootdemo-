package com.example.bootdemo.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 在SpringBoot中通过注解注册的方式简单的使用Filter
 * 过滤器和拦截器都属于面向切面编程的具体实现。而两者的主要区别包括以下几个方面：
 * 1、Filter是依赖于Servlet容器，属于Servlet规范的一部分，而拦截器则是独立存在的，可以在任何情况下使用。
 * 2、Filter的执行由Servlet容器回调完成，而拦截器通常通过动态代理的方式来执行。
 * 3、Filter的生命周期由Servlet容器管理，而拦截器则可以通过IoC容器来管理，因此可以通过注入等方式来获取其他Bean的实例，因此使用会更方便。
 * @author qq491
 */
@WebFilter(urlPatterns = "/*", filterName = "myFilter")
public class SpringFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("---------------------------->Filter初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println("---------------------------->开始进行过滤处理 请求地址"+req.getRequestURI());
//        调用该方法后，表示过滤器经过原来的url请求处理方法
//        req.getRequestDispatcher("/user/whoami").forward(servletRequest, servletResponse);
//        执行上述代码将把请求变更为""中的内容
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("---------------------------->Filter销毁");
    }
}


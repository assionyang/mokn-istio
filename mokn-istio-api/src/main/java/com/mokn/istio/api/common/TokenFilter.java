package com.mokn.istio.api.common;

import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 请求过滤器，增加跨域请求支持
 */
@Order(1)
@WebFilter(filterName = "TokenFilter")
public class TokenFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse)servletResponse;
        //充许跨域，*为不限
        response.setHeader("Access-Control-Allow-Origin", "*");
        //预简请求响应，设置充许的头和方法
        if("OPTIONS".equals(request.getMethod())){
            response.setHeader("Access-Control-Allow-Headers", "Authorization,Origin, X-Requested-With, Content-Type, Accept");
            response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS");
            response.setHeader("Access-Control-Max-Age","3600");
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out=response.getWriter();
            out.write("{\"code\":\"200\",\"message\":\"预检请求响应\"}");
            out.close();
        }
        filterChain.doFilter(request,response);
        return;
    }

    public void destroy() {

    }
}

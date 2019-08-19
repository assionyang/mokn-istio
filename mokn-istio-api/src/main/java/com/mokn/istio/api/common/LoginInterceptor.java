package com.mokn.istio.api.common;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录验证器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否为方法
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){
            //判断是否标注了登录验证注解
            LoginPassport loginPassport=((HandlerMethod)handler).getMethodAnnotation(LoginPassport.class);
            if(loginPassport==null || loginPassport.valid()==false){
                //如果未加登录验证注解或者注解是否验证为false，则跳过并返回
                return true;
            }
            //取得HTTP头中的Authorization中保存的token
            String token=request.getHeader("Authorization");
            if(token==null || token.equals("")){
                //如果token为空，返回400未登录
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out=response.getWriter();
                out.write("{\"code\":\"400\",\"message\":\"未登录\"}");
                out.close();
                return false;
            }else{
                if(jwtUtil==null){
                    BeanFactory factory=WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
                    jwtUtil=(JwtUtil)factory.getBean(JwtUtil.class);
                }

                token=token.replace("\"",""); // 去除多余字符
                if(!jwtUtil.validateToken(token)){
                    //token 验证无效，返回登录失效
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out=response.getWriter();
                    out.write("{\"code\":\"400\",\"message\":\"登录失效\"}");
                    out.close();
                    return false;
                }

                if(loginPassport.role()!=null && !loginPassport.role().equals("")){
                    String role=jwtUtil.getValueFromToken(token,"role")==null?"":jwtUtil.getValueFromToken(token,"role").toString().trim();
                    String[] roles=loginPassport.role().split("\\|");
                    boolean isRole=false;
                    for(String r:roles){
                        if(r.equals(role)){
                            isRole=true;
                        }
                    }
                    if(!isRole){
                        response.setContentType("application/json;charset=UTF-8");
                        PrintWriter out=response.getWriter();
                        out.write("{\"code\":\"401\",\"message\":\"权限不足\"}");
                        out.close();
                        return false;
                    }
                }
                return true;
            }
        }else{
            return true;
        }
    }
}

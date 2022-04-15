package csu.software.meetingsystem.jwt;


import com.sun.jmx.interceptor.DefaultMBeanServerInterceptor;
import csu.software.meetingsystem.entity.User;
import csu.software.meetingsystem.jwt.JwtUtils;
import csu.software.meetingsystem.jwt.PassToken;
import csu.software.meetingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.security.SignatureException;
import java.util.Map;


public class JwtAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService accountService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        String token = httpServletRequest.getHeader("Authorization");

        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        System.out.println(object.toString());
        //检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {

            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //默认全部检查
        else {
            System.out.println("被jwt拦截需要验证");
            // 执行认证
            if (token == null) {
                System.out.println("token null");

                throw new SignatureException("token error");
            }

            // 获取 token 中的 user Name
            String userId = JwtUtils.getAudience(token);

            //找找看是否有这个user
            User user_1 = new User();
            user_1.setId(Integer.parseInt(userId));
            User user = accountService.selectUser(user_1);

            if (user == null) {
                System.out.println("user null");
                //这个错误也是我自定义的
                throw new Exception();
            }

            // 验证 token
            JwtUtils.verifyToken(token, userId);

//            //获取载荷内容
//            String userName = JwtUtils.getClaimByName(token, "userName").asString();
//            String realName = JwtUtils.getClaimByName(token, "realName").asString();
//
//            //放入attribute以便后面调用
            httpServletRequest.setAttribute("id", userId);
//            request.setAttribute("realName", realName);


            return true;

        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}

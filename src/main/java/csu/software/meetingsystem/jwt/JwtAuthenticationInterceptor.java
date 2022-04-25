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
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        else {
            if (token == null) {
                throw new SignatureException("token error");
            }
            String userId = JwtUtils.getAudience(token);
            User user_1 = new User();
            user_1.setId(Integer.parseInt(userId));
            User user = accountService.selectUser(user_1);
            if (user == null) {
                throw new Exception();
            }
            JwtUtils.verifyToken(token, userId);
            httpServletRequest.setAttribute("id", userId);
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

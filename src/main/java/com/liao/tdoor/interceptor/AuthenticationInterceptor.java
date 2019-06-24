package com.liao.tdoor.interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.liao.tdoor.annotation.PassToken;
import com.liao.tdoor.annotation.UserLoginToken;
import com.liao.tdoor.dao.UserDao;
import com.liao.tdoor.model.CommonException;
import com.liao.tdoor.model.User;
import com.liao.tdoor.responseMsg.CurrentUserConstants;
import com.liao.tdoor.util.tokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 拦截器，拦截token
 * @author 廖某某
 * @date 2019/02/18
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object object) throws JsonProcessingException {
        //设置允许哪些域名应用进行ajax访问
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, content-Type, Accept, Authorization");
        httpServletResponse.setHeader("Access-Control-Max-Age","3600");
        httpServletResponse.setHeader("Access-Control-Expose-Headers","Authorization");
        //获取请求头的token
        String token=httpServletRequest.getHeader("Authorization");
        //如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod) object;
        Method method=handlerMethod.getMethod();
        //检查是否有passToken注释，有则跳过验证
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken=method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        //检查是否有需要用户权限的注解
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken=method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required()){
                //执行认证
                if(token==null){
                    throw new RuntimeException("无token，请重新登录");
                }else {
                    //从缓存中查看token是否过期
                    Claims claims;
                    try{
                        claims=tokenUtil.parseJWT(token);
                    }catch (ExpiredJwtException e){
                        httpServletResponse.setStatus(410);
                        String new_token=tokenUtil.RefreshToken(token);
                        httpServletResponse.setHeader("Authorization",new_token);
                        throw new RuntimeException("410");
                    }catch (SignatureException e){
                        httpServletResponse.setStatus(411);
                        throw new RuntimeException("token错误，请重新登录");
                    }
                    String user_email=claims.getId();
                    User user=userDao.isExistUser(user_email);
                    if(user==null){
                        httpServletResponse.setStatus(401);
                        throw new RuntimeException("用户不存在，请重新登录");
                    }
                    //当前登录用户 @CurrentUser
                    httpServletRequest.setAttribute(CurrentUserConstants.CURRENT_USER,user);
                }
            }
        }
        return true;
    }
    // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    // 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e)throws Exception{

    }
}

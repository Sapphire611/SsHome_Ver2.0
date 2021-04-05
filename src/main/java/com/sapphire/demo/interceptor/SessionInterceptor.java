package com.sapphire.demo.interceptor;

import com.sapphire.demo.mapper.UserMapper;
import com.sapphire.demo.model.User;
import com.sapphire.demo.model.UserExample;
import com.sapphire.demo.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NotificationService notificationService;
    
    @Value("${href}")
	private String href;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Cookie[] cookies = request.getCookies();
    	
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    
                    // 创建example，选择token和user_token一样的的User;
					UserExample userExample = new UserExample();
					userExample.createCriteria().andTokenEqualTo(token);
					List<User> users = userMapper.selectByExample(userExample);
                    
                    if (users.size() != 0) {
                        request.getSession().setAttribute("user", users.get(0));
                        Long unreadCount = notificationService.unreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }else {
        	
        }
        return true;
    }



    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	request.getSession().setAttribute("loginURL", href);
  
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	
    }
}

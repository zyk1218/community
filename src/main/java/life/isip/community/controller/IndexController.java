package life.isip.community.controller;

import life.isip.community.mapper.UserMapper;
import life.isip.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 该Controller主要作用是控制主页面URI,
 * 根据token判断用户的登录状态
 */

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("token")){
                String token = cookie.getValue();
                UserModel user = userMapper.findUserByToken(token);
                //用户已存在：
                if(user != null){
                    request.getSession().setAttribute("gitHubUser",user);
                }
                //用户未存在（代指各种意外情况）
                if(user == null){
                    request.getSession().removeAttribute("gitHubUser");
                }
            }
        }
        return "index";
    }
}

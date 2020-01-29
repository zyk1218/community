package life.isip.community.controller;

import life.isip.community.dto.QuestionDTO;
import life.isip.community.mapper.QuestionMapper;
import life.isip.community.mapper.UserMapper;
import life.isip.community.model.QuestionModel;
import life.isip.community.model.UserModel;
import life.isip.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
  * @author remember
  * @date 2020/1/29 20:03
  * 该Controller主要作用是控制主页面URI,
  * 根据token判断用户的登录状态
  */

@Controller
public class IndexController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,Model model){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    UserModel user = userMapper.findUserByToken(token);
                    //user exists ::::
                    if(user != null){
                        request.getSession().setAttribute("gitHubUser",user);
                    }
                    //user not exists (all unexpected cases) ::::
                    if(user == null){
                        request.getSession().removeAttribute("gitHubUser");
                    }
                }
            }
        }
        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}

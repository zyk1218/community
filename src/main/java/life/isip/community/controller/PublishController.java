package life.isip.community.controller;


import life.isip.community.mapper.QuestionMapper;
import life.isip.community.mapper.UserMapper;
import life.isip.community.model.QuestionModel;
import life.isip.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
  * @author remember
  * @date 2020/1/26 9:52
  */

@Controller
public class PublishController {


    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;
    //当请求是以get提交时就渲染页面。
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    //当请求是以post提交时就执行发布功能
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        //如果填写缺少选项，那么就返回发布页面::::
        if(title == null || title == ""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description == null || description == ""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag == null || tag == ""){
            model.addAttribute("error","问题标签不能为空");
            return "publish";
        }



        //get current user ::::
        UserModel user = null;
        Cookie[] cookies = request.getCookies();
        if(cookies.length != 0 && cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.findUserByToken(token);
                    break;
                }
            }
        }
        //user not exists ::::
        if(user == null){
            model.addAttribute("error","user not load.Plz load first");
            return "publish";
        }
        //user exists ::::
        if(user != null){
            request.getSession().setAttribute("user",user);
        }

        QuestionModel questionModel = new QuestionModel();
        questionModel.setDescription(description);
        questionModel.setGmtCreate(System.currentTimeMillis());
        questionModel.setGmtModified(questionModel.getGmtCreate());
        questionModel.setTag(tag);
        questionModel.setCreator(user.getId());
        questionModel.setTitle(title);
        questionMapper.createQuestion(questionModel);
        return "redirect:/";
    }
}

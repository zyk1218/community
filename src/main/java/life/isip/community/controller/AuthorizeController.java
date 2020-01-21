package life.isip.community.controller;


import life.isip.community.dto.AccessTokenDTO;
import life.isip.community.dto.GitHubUser;
import life.isip.community.mapper.UserMapper;
import life.isip.community.model.UserModel;
import life.isip.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;


/**
 * 该Controller主要作用是获取GitHub上用户信息。
 */


@Controller
public class AuthorizeController {


    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String client_id;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.redirect.uri}")
    private String redirect_uri;

    @GetMapping("/callback")
    public String callback(
            @RequestParam(name = "code") String code,
            @RequestParam(name = "state") String state,
            HttpServletRequest request,
            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        //该accessToken是为了获取gitHub认证。
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if(gitHubUser != null){
            UserModel userModel = new UserModel();
            //该token是检测用户是否登录。
            String token = UUID.randomUUID().toString();
            userModel.setToken(token);
            userModel.setName(gitHubUser.getName());
            userModel.setAccountId(gitHubUser.getId().toString());
            userModel.setGmtCreate(System.currentTimeMillis());
            userModel.setGmtmodified(userModel.getGmtCreate());
            userMapper.insertUser(userModel);
            //登录成功
            request.getSession().setAttribute("gitHubUser",gitHubUser); //将该用户添加到session中
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}

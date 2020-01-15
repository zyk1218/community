package life.isip.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 该Controller主要作用是控制主页面URI。
 */

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}

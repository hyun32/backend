package com.hit.community.controller;

import com.hit.community.session.LoginUser;
import com.hit.community.session.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {

    @GetMapping("/")
    public String index(Model model, @LoginUser UserSession user){
        return "index";
    }

}

package com.github.cunvoas.iam.web.front.home;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.cunvoas.iam.web.front.util.Constants;

@Controller
public class IndexController implements Constants {
    
    @RequestMapping(value="/")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        return REDIRECT_PREFIX+"/admin-iam/home.do";
    }

}

package com.numbguy.qa.controller;

import com.numbguy.qa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    @RequestMapping(path={"/reg/"}, method = {RequestMethod.POST})
    public String register(Model model, @RequestParam("username") String userName,
                           @RequestParam("password") String password,
                           @RequestParam(value = "next", required = false) String next,
                           HttpServletResponse response) {

        try {
            Map<String, String> map = userService.register(userName, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            return "login";
        }
    }

    @RequestMapping(path={"/reglogin"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String register(Model model,
                           @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        System.out.println("reglogin");
        return "login";
    }

    @RequestMapping(path={"/login/"}, method = {RequestMethod.POST})
    public String login(Model model, @RequestParam("username") String userName,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "rememberme", defaultValue = "false") boolean rememberme,
                        HttpServletResponse response) {

        try {
            Map<String, String> map = userService.login(userName, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                System.out.println("next: " + next);
                if(!StringUtils.isEmpty(next))
                    return "redirect:" + next;
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            return "login";
        }
    }

    @RequestMapping(path = {"logout"}, method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }
}

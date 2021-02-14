package com.goltsov.controller;

import com.goltsov.model.Greeting;
import com.goltsov.model.UserLog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;

@Controller
public class FindUserController {

    @GetMapping("/greeting2")
    public String greetingForm(Model model, @RequestHeader(value="User-Agent", required=false) String userAgent) {
        model.addAttribute("greeting2", new Greeting());
        return "greeting";
    }

    @PostMapping("/greeting2")
    public String greeting2Submit(@ModelAttribute Greeting greeting) {
        UserLog userLog = new UserLog();
        greeting.setCurrentDate(new Date().toString());

        return userLog.findUser(greeting);
    }
}

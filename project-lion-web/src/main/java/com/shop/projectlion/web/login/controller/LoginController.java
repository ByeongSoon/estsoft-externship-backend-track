package com.shop.projectlion.web.login.controller;

import com.shop.projectlion.global.error.exception.BusinessException;
import com.shop.projectlion.web.login.dto.MemberRegisterDto;
import com.shop.projectlion.web.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

    @Autowired
    private final LoginService loginService;

    @GetMapping("/login")
    public String login(String errorMessage,
                        Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "login/loginform";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("memberRegisterDto", new MemberRegisterDto());
        return "login/registerform";
    }

    @PostMapping("/register")
    public String register(@Validated MemberRegisterDto request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "login/registerform";
        }

        try {
            loginService.register(request);
        } catch (BusinessException e) {
            model.addAttribute("errorMessage",e.getMessage());
            return "login/registerform";
        }

        return "redirect:/login";

    }

}

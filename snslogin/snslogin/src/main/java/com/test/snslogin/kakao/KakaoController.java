package com.test.snslogin.kakao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class KakaoController {

    @Autowired
    private KakaoService kakaoService;

    @RequestMapping("/do")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/token")
    public String getCI(@RequestParam String code, Model model) throws IOException {
        User user = kakaoService.getKakaoToken(code);
        model.addAttribute("id", user.getId());
        model.addAttribute("nickname", user.getNickname());
        model.addAttribute("email", user.getEmail());
        return "index";
    }

}


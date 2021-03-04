package com.miu.controller;


import com.miu.entity.AuthUser;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * 認証処理用のコントローラです。
 */
@Controller
public class AuthController {

  @GetMapping("/")
  public String index(@AuthenticationPrincipal AuthUser user) {
    // ログイン済みならtop画面へ
    if (user != null) {
      return "redirect:/top";
    }
    return "redirect:/login";
  }

  @RequestMapping("/login")
  public String login(@RequestParam(value = "error", required = false) String error,
                      @RequestParam(value = "logout", required = false) String logout,
                      Model model, HttpSession session) {

    model.addAttribute("showErrorMsg", false);
    model.addAttribute("showLogoutedMsg", false);

    if (error != null) {
      if (session != null) {
        AuthenticationException ex = (AuthenticationException) session
            .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        if (ex != null) {
          model.addAttribute("showErrorMsg", true);
          model.addAttribute("errorMsg", ex.getMessage());
        }
      }
    } else if (logout != null) {
      model.addAttribute("showLogoutedMsg", true);
    }
    return "login";
  }



}

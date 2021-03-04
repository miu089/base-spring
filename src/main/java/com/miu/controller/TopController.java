package com.miu.controller;

import com.miu.entity.AuthUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class TopController {

  @RequestMapping("/top")
  public String top(@RequestParam HashMap<String, String> params,
                    Model model,
                    @AuthenticationPrincipal AuthUser authUser) {
    model.addAttribute("user", authUser);

    return "top";
  }

}

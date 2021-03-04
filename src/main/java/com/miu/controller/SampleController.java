package com.miu.controller;


import com.miu.dao.TestTableDao;
import com.miu.entity.TestTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class SampleController {
  @Autowired
  TestTableDao testTableDao;


  @RequestMapping("/sample/index")
  public String index(@RequestParam HashMap<String, String> params,
                      Model model
                      /*@AuthenticationPrincipal AuthUser authUser*/) {

    List<TestTable> testTableList = testTableDao.selectAll();
    model.addAttribute("testTableList", testTableList);

    return "sample/index";
  }


}

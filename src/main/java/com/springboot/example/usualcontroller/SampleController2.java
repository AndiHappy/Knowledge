package com.springboot.example.usualcontroller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springboot.example.dao.UsualDao;
import com.springboot.example.usualserver.UsualServer;

@Controller

// The second class-level annotation is @EnableAutoConfiguration.
// This annotation tells Spring Boot to “guess” how you will want
// to configure Spring, based on the jar dependencies that you have added.

public class SampleController2 {

  @Autowired
  // @Resource
  public UsualServer usualServer;

  @Resource
  public UsualDao usualDao;

  @RequestMapping(value = "/hello", method = RequestMethod.GET)
  @ResponseBody
  String home(@RequestParam(value = "name", required = false) String name) {
    return usualServer.getDaoTest(name);
  }

  @RequestMapping(value = "/hello1", method = RequestMethod.GET)
  @ResponseBody
  String home1(@RequestParam(value = "name", required = false) String name) {
    return usualDao.getDao(name);
  }
}
package com.dahafo.web.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dahafo.web.entity.UserEntity;
import com.dahafo.web.service.IUserService;


@Controller
@RequestMapping(value="/")
public class UserController {
	@Resource
	private IUserService userService;
	
	@RequestMapping("/")
	public ModelAndView getAll(ModelAndView view){
		List<UserEntity> list=this.userService.selectAll(new UserEntity());
		view.addObject("user", list.get(0));
		view.setViewName("index");
		return view;
	}
}

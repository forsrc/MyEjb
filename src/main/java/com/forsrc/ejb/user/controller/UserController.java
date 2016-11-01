package com.forsrc.ejb.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.forsrc.ejb.user.service.UserServiceLocal;
import com.forsrc.exception.ServiceException;
import com.forsrc.pojo.User;

@Controller
@RequestMapping(value = "/v1.0/user")
public class UserController {

	@EJB(mappedName = "java:app/MyEjb/UserServiceImpl!com.forsrc.ejb.user.service.UserServiceLocal")
	private UserServiceLocal userService;
	
	@RequestMapping(value = { "/new" }, method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public String save(HttpServletRequest request,
			HttpServletResponse response) {
		
		User u = new User();
		u.setUsername(new Date().toString());
		u.setPassword("123456");
		u.setEmail("admin@admin.net");
		try {
			userService.save(u);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return String.valueOf(u.getId());
	}


	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public String get(@PathVariable Long id, HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> message = new HashMap<String, Object>();
		try {
			message.put(String.valueOf(id), userService.get(id));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return message.toString();
	}

	@RequestMapping(value = { "" }, method = RequestMethod.GET)
	@ResponseBody
	public String list(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> message = new HashMap<String, Object>();
		try {
			message.put(new Date().toString(), userService.list(0, 10));
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return message.toString();
	}

}

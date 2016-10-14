package com.forsrc.ejb.controller;

import com.forsrc.ejb.local.HelloWorldLocal;
import com.forsrc.ejb.local.impl.HelloWorldLocalImpl;
import com.forsrc.ejb.remote.HelloWorldRemote;
import com.forsrc.ejb.remote.impl.HelloWorldRemoteImpl;
import com.forsrc.ejb.stateless.HelloWorldBean;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/v1.0/myejb")
public class MyEjbController {

	@EJB(mappedName="java:app/MyEjb/HelloWorldBean")
    private HelloWorldBean helloWorldBean ;
	@Autowired
	//@EJB(mappedName="java:app/MyEjb/HelloWorldLocalImpl")
    private HelloWorldLocal helloWorldLocal;
	@Autowired
	//@EJB(mappedName="java:app/MyEjb/HelloWorldRemoteImpl")
    private HelloWorldRemote helloWorldRemote;
    
    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    @ResponseBody
    public String test(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> message = new HashMap<String, String>();
        if(helloWorldBean == null) {
        	helloWorldBean = new HelloWorldBean();
        	message.put("message", "new HelloWorldBean()");
        } else {
        	message.put("message", "EJB");
        }
        message.put("hell", helloWorldBean.hello(new Date().toString()));
        modelAndView.addObject("return", message);
        modelAndView.addObject("status", 200);
        return message.toString();
    }
    
    
    @RequestMapping(value = {"/testLocal"}, method = RequestMethod.GET)
    @ResponseBody
    public String testLocal(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> message = new HashMap<String, String>();
        if(helloWorldLocal == null) {
        	helloWorldLocal = new HelloWorldLocalImpl();
        	message.put("message", "new HelloWorldLocalImpl()");
        } else {
        	message.put("message", "@Autowired");
        }
        message.put("hell", helloWorldLocal.hello(new Date().toString()));
        modelAndView.addObject("return", message);
        modelAndView.addObject("status", 200);
        return message.toString();
    }
    
    @RequestMapping(value = {"/testRemote"}, method = RequestMethod.GET)
    @ResponseBody
    public String testRemote(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> message = new HashMap<String, String>();
        if(helloWorldRemote == null) {
        	helloWorldRemote = new HelloWorldRemoteImpl();
        	message.put("message", "new HelloWorldRemoteImpl()");
        } else {
        	message.put("message", "@Autowired");
        }
        message.put("hell", helloWorldRemote.hello(new Date().toString()));
        modelAndView.addObject("return", message);
        modelAndView.addObject("status", 200);
        return message.toString();
    }
    

}

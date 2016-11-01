package com.forsrc.ejb.demo.service.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.forsrc.ejb.demo.service.HelloService;
import com.forsrc.ejb.demo.service.HelloWorldService;


@Stateless
@Local(HelloWorldService.class)
@Remote(HelloService.class)
public class HelloWorldServiceImpl implements HelloWorldService, HelloService{

	@Override
	public String hello(String name) {
 
		return this.getClass().toString() + " --> " + name;
	}

}

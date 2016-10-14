package com.forsrc.ejb.service.impl;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.forsrc.ejb.service.HelloWorldService;


@Stateless
//@Local(HelloWorldService.class)
//@Remote(HelloWorldService.class)
public class HelloWorldServiceImpl implements HelloWorldService{

	@Override
	public String hello(String name) {
 
		return this.getClass().toString() + " --> " + name;
	}

}

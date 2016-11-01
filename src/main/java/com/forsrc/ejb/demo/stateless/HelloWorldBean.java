package com.forsrc.ejb.demo.stateless;

import javax.ejb.Stateless;

@Stateless
public class HelloWorldBean {

	public String hello(String name) {
		return this.getClass().toString() + " --> " + name;
	}

}

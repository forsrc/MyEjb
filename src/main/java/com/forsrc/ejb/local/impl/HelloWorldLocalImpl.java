package com.forsrc.ejb.local.impl;

import javax.ejb.Local;
import javax.ejb.Stateless;

import com.forsrc.ejb.local.HelloWorldLocal;

@Stateless
@Local(HelloWorldLocal.class)
public class HelloWorldLocalImpl implements HelloWorldLocal {

	@Override
	public String hello(String name) {

		return this.getClass().toString() + " --> " + name;
	}

}
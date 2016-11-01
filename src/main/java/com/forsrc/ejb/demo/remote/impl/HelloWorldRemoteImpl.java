package com.forsrc.ejb.demo.remote.impl;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.forsrc.ejb.demo.remote.HelloWorldRemote;

@Stateless
@Remote(HelloWorldRemote.class)
public class HelloWorldRemoteImpl implements HelloWorldRemote{

	@Override
	public String hello(String name) {
 
		return this.getClass().toString() + " --> " + name;
	}

}
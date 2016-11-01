package com.forsrc.ejb.demo.service;

import javax.ejb.Local;
import javax.ejb.Remote;


@Local
public interface HelloWorldService extends HelloService{
	public String hello(String name);
}

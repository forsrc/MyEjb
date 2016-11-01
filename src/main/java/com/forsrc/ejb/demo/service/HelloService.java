package com.forsrc.ejb.demo.service;

import javax.ejb.Local;
import javax.ejb.Remote;


@Remote
public interface HelloService {
	public String hello(String name);
}

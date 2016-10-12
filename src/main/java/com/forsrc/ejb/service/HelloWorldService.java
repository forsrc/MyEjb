package com.forsrc.ejb.service;

import javax.ejb.Local;
import javax.ejb.Remote;


//@Local
//@Remote
public interface HelloWorldService {
	public String hello(String name);
}

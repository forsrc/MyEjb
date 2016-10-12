package com.forsrc.ejb.local;

import javax.ejb.Local;


@Local
public interface HelloWorldLocal {
	public String hello(String name);
}

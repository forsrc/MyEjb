package com.forsrc.ejb.remote;

import javax.ejb.Remote;

//@Remote
public interface HelloWorldRemote {
	public String hello(String name);
}

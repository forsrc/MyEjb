package com.forsrc.ejb.demo.webservice;

import javax.ejb.Remote;

@Remote
public interface HelloWorldEjb {


	public String hello(String name) ;
}

package com.forsrc.ejb.demo.webservice;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;

@Stateless
@Remote(HelloWorldEjb.class)
public class HelloWorldEjbImpl implements HelloWorldEjb {

	@EJB(mappedName = "java:app/MyEjb/HelloWorldWebService!com.forsrc.ejb.demo.webservice.HelloWorldWebService")
	private HelloWorldWebService helloWorldWebService;

	public String hello(String name) {
		return helloWorldWebService.hello(name);
		//return name;
	}

}

package com.forsrc.ejb.demo.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://webservice.ejb.forsrc.com/wsdl")
public interface HelloWorldWebService extends java.rmi.Remote{
	@WebMethod(operationName = "hello")
	public String hello(String name);
}

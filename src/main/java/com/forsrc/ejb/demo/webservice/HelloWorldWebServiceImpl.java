package com.forsrc.ejb.demo.webservice;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebService;

@Stateless(name = "HelloWorldWebService")
@WebService(
        portName = "HelloWorldWebServicePort",
        serviceName = "HelloWorldWebService",
        targetNamespace = "http://webservice.ejb.forsrc.com/wsdl",
        endpointInterface = "com.forsrc.ejb.demo.webservice.HelloWorldWebService"
)
@Remote(HelloWorldWebService.class)
public class HelloWorldWebServiceImpl implements HelloWorldWebService {

	@Override
	public String hello(String name) {
		return "hello -> " + name;
	}

}

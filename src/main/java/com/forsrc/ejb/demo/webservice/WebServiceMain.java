package com.forsrc.ejb.demo.webservice;

import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.forsrc.ejb.demo.remote.HelloWorldRemote;

public class WebServiceMain {

	public static void main(String[] args) throws MalformedURLException {
		test();
		testWebService();
	}

	public static void test() {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL,
				"http-remoting://localhost:8075");
		// jndiProperties.put("remote.connection.default.host", "localhost");
		// jndiProperties.put(Context.SECURITY_PRINCIPAL, "admin");
		// jndiProperties.put(Context.SECURITY_CREDENTIALS, "YWRtaW4=");

		InitialContext ctx;
		try {
			final Context context = new InitialContext(jndiProperties);

			HelloWorldEjb helloworld = (HelloWorldEjb) context
					.lookup("MyEjb/HelloWorldEjbImpl!com.forsrc.ejb.demo.webservice.HelloWorldEjb");
			System.out.println(helloworld.hello("webservice HelloWorldEjb"));
			context.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static void testWebService() throws MalformedURLException {
		PrintStream out = System.out;
		
		QName qname = new QName("http://webservice.ejb.forsrc.com/wsdl", "HelloWorldWebService");
        Service helloPojoService = Service.create(new URL("http://localhost:8075/MyEjb/HelloWorldWebService/HelloWorldWebServiceImpl?wsdl"), qname);
        HelloWorldWebService helloPojo = helloPojoService.getPort(HelloWorldWebService.class);
        out.println();
        out.println("Pojo Webservice");
        out.println("    helloPojo.hello(\"Bob\")=" + helloPojo.hello("Bob"));
        out.println("    helloPojo.hello(null)=" + helloPojo.hello(null));
        out.println();

	}
}

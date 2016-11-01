package com.forsrc.ejb.demo.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.forsrc.ejb.demo.remote.HelloWorldRemote;

public class JboosMain {

	public static void main(String[] args) {
		service();
	}

	public static void service() {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL,"http-remoting://localhost:8075");
		//jndiProperties.put("remote.connection.default.host", "localhost");
		//jndiProperties.put(Context.SECURITY_PRINCIPAL, "admin");
		//jndiProperties.put(Context.SECURITY_CREDENTIALS, "YWRtaW4=");

		InitialContext ctx;
		try {
			final Context context = new InitialContext(jndiProperties);

			HelloWorldRemote helloworld = (HelloWorldRemote) context
					.lookup("MyEjb/HelloWorldRemoteImpl!com.forsrc.ejb.demo.remote.HelloWorldRemote");
			System.out.println(helloworld.hello("service HelloWorld"));
			context.close();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}

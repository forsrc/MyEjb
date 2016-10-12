package com.forsrc.ejb.client;

import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.forsrc.ejb.local.HelloWorldLocal;
import com.forsrc.ejb.remote.HelloWorldRemote;
import com.forsrc.ejb.service.HelloWorldService;

public class JboosMain {

	public static void main(String[] args) {
		service();
	}

	public static void local() {
		Properties props = new Properties();
		props.setProperty(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");

		InitialContext ctx;
		try {
			ctx = new InitialContext(props);
			HelloWorldLocal helloworld = (HelloWorldLocal) ctx
					.lookup("HelloWorldLocal/local");
			System.out.println(helloworld.hello("local"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static void remote() {
		Properties props = new Properties();
		props.setProperty(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");

		InitialContext ctx;
		try {
			ctx = new InitialContext(props);
			HelloWorldRemote helloworld = (HelloWorldRemote) ctx
					.lookup("HelloWorldRemote/remote");
			System.out.println(helloworld.hello("local"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public static void service() {
		final Hashtable jndiProperties = new Hashtable();
		jndiProperties.put(Context.URL_PKG_PREFIXES,
				"org.jboss.ejb.client.naming");
		//jndiProperties.put(Context.PROVIDER_URL,"t3://localhost:8075");
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");

		InitialContext ctx;
		try {
			final Context context = new InitialContext(jndiProperties);

			HelloWorldService helloworld = (HelloWorldService) context.lookup("ejb:/MyEjb/HelloWorldService!"
					+ HelloWorldService.class.getName());
 
			System.out.println(helloworld.hello("service"));
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

}

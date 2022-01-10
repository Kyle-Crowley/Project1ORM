package com.revature.util;

import com.revature.dao.Session;

public class SessionFactory 
{
	private String databaseURL="";
	private String databaseUser="";
	private String databasePassword="";
	
	public SessionFactory() 
	{
		
	}
	
	
	
	public SessionFactory(String databaseURL, String databaseUser, String databasePassword) 
	{
		super();
		this.databaseURL = databaseURL;
		this.databaseUser = databaseUser;
		this.databasePassword = databasePassword;
	}



	public Session createSession() //create a session object and provide a connection object
	{
		Session newSession = new Session();
		newSession.getConnection(databaseURL, databaseUser, databasePassword);
		return newSession;
	}
	
	public void checkConnection(Session thisSession)
	{
		thisSession.getConnection(databaseURL, databaseUser, databasePassword);
	}
}

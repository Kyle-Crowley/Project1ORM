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
	
	public Session createSession()
	{
		return new Session();
	}
}

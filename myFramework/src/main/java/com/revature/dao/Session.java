package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Session 
{
	private static Connection conn = null;
	private static Transaction transaction;
	
	public Session()
	{
		super();
	}
	
	public Session createSession(String databaseURL,String databaseUser,String databasePassword)
	{
		//right now the session only needs to create a connection to initialize itself
		//establish connection with database
		try 
		{
			if(conn != null && !conn.isClosed())
			{
				return this; //TODO: if any other things are added to session remove the return
			}
			else
			{
				conn = DriverManager.getConnection(databaseURL,databaseUser,databasePassword);
			}
		} 
		catch (SQLException e) 
		{
			System.out.println("Couldn't establish connection with database");
			e.printStackTrace();
		}
		
		//return reference to this object once done
		return this;
	}
	
	
	public void doSomething()
	{
		System.out.println("This session just did something");
	}
	
	//database actions here
	/*
	 * save()/insert()	-create
	 * update(Object object) saveOrUpdate?
	 * delete(Object object)
	 * select()/various select statements	-read
	 * creatSQLQuery() - maybe? if it's not overly complex
	 */
	public int create()
	{
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
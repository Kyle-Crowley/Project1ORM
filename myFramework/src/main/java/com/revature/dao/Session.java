package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

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
	
	public int save()
	{
		return 0;
	}
	
	public int saveOrUpdate()//look before saving to avoid duplicates or uniqueness errors
	{
		return 0;
	}
	
	public Optional<?> select(Object obj)
	{
		return null;
	}
	
	public boolean delete(Object obj,String tableName) //requires the object and table name, will delete the first one found if duplicate
	{
		return false;
	}
	
	public boolean delete(Object obj) //requires the object, lookup table name from entity annotation to find location
	{
		return false;
	}
	
	
	/*
	 * custom query for all the things I can't plan for
	 * takes in a prepared statement from the user and returns
	 * the result set back, returns null if result set is null
	 * or errors occur
	 */
	public ResultSet customQuery(PreparedStatement customStatement)
	{
		try 
		{
			ResultSet rs;
			if((rs = customStatement.executeQuery()) != null)
			{
				return rs;
			}
			else
			{
				return null;
			}	
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return null;
		}
	}
	

	
}

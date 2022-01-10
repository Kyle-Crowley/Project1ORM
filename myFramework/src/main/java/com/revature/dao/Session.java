package com.revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.postgresql.util.PSQLException;

public class Session 
{
	private static Connection conn = null;
	private static Transaction transaction;
	
	public Session()
	{
		super();
	}
	
	public void getConnection(String databaseURL,String databaseUser,String databasePassword) //attempt connect to db, if not already connected
	{
		try 
		{
			if(conn != null && !conn.isClosed())
			{
				return;
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
	
	public int save(Object obj)
	{
		
		//insert statement
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
	 * 
	 * DOES NOT TAKE PRIMITIVE TYPES
	 */
	public ResultSet customQuery(String query,List<Object> values)
	{
		try 
		{
			if(conn == null)
			{
				
			}
			PreparedStatement customStatement = conn.prepareStatement(query);
			
			if(!values.isEmpty())//check values for values
			{
				for(int i = 0; i < values.size(); i++) // insert values to statement, check for data type :/
				{
					
					if(values.get(i) instanceof Object)
					{
						customStatement.setObject(i+1,(Object) values.get(i));
					}
					else
					{
						System.out.println("not a primitive object wrapper");
					}
				}
			}
			
			
			ResultSet rs;
			if((rs = customStatement.executeQuery()) != null)
			{
				return rs;
			}
			else
			{
				return null;
			}	
			
		} catch (PSQLException e) 
		{
			System.out.println("No results returned by query!");
			return null;
		} catch(SQLException e)
		{
			e.printStackTrace();
			return null;
		}
	}
	

	
}

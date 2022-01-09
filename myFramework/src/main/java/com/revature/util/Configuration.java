package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Configuration 
{
	//read the config file and define all the default parameters and instantiate the session factory
	private static final SessionFactory thisSessionFactory = new SessionFactory();
	
	//configurable variables
	private String databaseType="";
	private String databaseDriver="";
	private String databaseURL="";
	private String databaseUser="";
	private String databasePassword="";
	//Configuration member variables
	Connection conn;
	//constructor
	
	
	public Configuration() 
	{
		super();
	}


	public Configuration configure(String filename) 
	{
		//1. get properties from configuration file and apply
		Properties prop = new Properties();
		try 
		{
			prop.load(new FileReader(filename));
			this.databaseType = prop.getProperty("databaseType");
			this.databaseDriver = prop.getProperty("databaseDriver");
			this.databaseURL = prop.getProperty("databaseURL");
			this.databaseUser = prop.getProperty("databaseUser");
			this.databasePassword = prop.getProperty("databasePassword");
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
			e.printStackTrace();
		}
		//2. make connection to database
		try
		{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
		} catch (SQLException e)
		{
			System.out.println("Connection Failed");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Database driver was not found");
			e.printStackTrace();
		} 
		
		//3. read meta models from project and create or update tables in database
		//TODO
		return this;
	}

	//getSessionFactory returns the instantiated SessionFactory object
	public SessionFactory getSessionFactory()
	{
		return thisSessionFactory;
	}


	public String getDatabaseType() {
		return databaseType;
	}


	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}


	public String getDatabaseDriver() {
		return databaseDriver;
	}


	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}


	public String getDatabaseURL() {
		return databaseURL;
	}


	public void setDatabaseURL(String databaseURL) {
		this.databaseURL = databaseURL;
	}


	public String getDatabaseUser() {
		return databaseUser;
	}


	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}


	public String getDatabasePassword() {
		return databasePassword;
	}


	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}
	
	
}

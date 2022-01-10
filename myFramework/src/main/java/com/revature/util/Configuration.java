package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import com.revature.annotations.Entity;
import com.revature.util.metamodels.ColumnField;
import com.revature.util.metamodels.ForeignKeyField;
import com.revature.util.metamodels.MetaModel;
import com.revature.util.metamodels.PrimaryKeyField;

public class Configuration 
{
	private static SessionFactory thisSessionFactory;
	private static EntityHandler thisEntityHandler;
	//configurable variables
	private String databaseType="";
	private String databaseDriver="";
	private String databaseURL="";
	private String databaseUser="";
	private String databasePassword="";
	//Configuration member variables
	Connection conn = null;
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
			System.out.println("Reading properties from file " + filename);
			prop.load(new FileReader(filename));
			this.databaseType = prop.getProperty("databaseType");
			this.databaseDriver = prop.getProperty("databaseDriver");
			this.databaseURL = prop.getProperty("databaseURL");
			this.databaseUser = prop.getProperty("databaseUser");
			this.databasePassword = prop.getProperty("databasePassword");
			//create session factory
			thisSessionFactory = new SessionFactory(databaseURL,databaseUser,databasePassword);
		} 
		catch (IOException e) 
		{
			System.out.println("File not found");
			e.printStackTrace();
		}
		//2. make connection to database
		try
		{
			System.out.println("Attempting connection to database");
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
		//ask entity handler for a list of annotated entities
		List<MetaModel<Class<?>>> metaList = thisEntityHandler.getMetaModels();
		//we only care if it's an entity,also that should be the only thing in the metaList anyways
		for (int i = 0; i < metaList.size(); i++) 
		{
			// grab class object
			MetaModel<Class<?>> currentClass = metaList.get(i);
			// grab the list of each field type from the meta model object
			List<ColumnField> columnList = currentClass.getColumns();
			List<ForeignKeyField> fkList = currentClass.getForeignKeys();
			PrimaryKeyField pkList = currentClass.getPrimaryKey();
			String tableName="";
			
			Annotation[] annotations = currentClass.getMetaClass().getAnnotations();
			for(Annotation a: annotations)
			{
				if(a.annotationType() == Entity.class)
				{
					tableName = a.toString();
				}
				
			}
			int first =tableName.indexOf('"',0);
			int second = tableName.indexOf('"',first+1);
			tableName = tableName.substring(first+1, second);
			
			
			
			String query = "CREATE TABLE " + tableName;
			//construct the variable query and then send the ordered value list to customQuery
			List<Object> values = new LinkedList<Object>();
			
			query += "( ? ? NOT NULL PRIMARY KEY";
			values.add(pkList.getName());
			values.add(pkList.getType());
			System.out.println(query);
			for(ColumnField c: columnList)
			{
				query+=",? ? NOT NULL";
				values.add(c.getName());
				if(c.getType() == String.class)
				{
					//A call to a function that returns the SQL equivalent of java classes would go here
					//values.add()
				}
				else
				{
					values.add(c.getType());
				}
				
				
				
				System.out.println(query);
			}
			for(ForeignKeyField fk : fkList)
			{
				query+=", ? ? NOT NULL REFERENCES ?";
				values.add(fk.getName());
				Class c = fk.getType();
				Object x = c.cast(fk.getClass());
				values.add(x);
				values.add(fk.getReference());
			}
			query+=");";
			System.out.println(query);
			
		}
		


		
		return this;
	}
	
	public static SessionFactory getThisSessionFactory() {
		return thisSessionFactory;
	}



	public static void setThisSessionFactory(SessionFactory thisSessionFactory) 
	{
		Configuration.thisSessionFactory = thisSessionFactory;
	}



	public static EntityHandler getThisEntityHandler() {
		return thisEntityHandler;
	}



	public static void setThisEntityHandler(EntityHandler thisEntityHandler) {
		Configuration.thisEntityHandler = thisEntityHandler;
	}



	public Connection getConn() {
		return conn;
	}



	public void setConn(Connection conn) {
		this.conn = conn;
	}



	public Connection getConnection()
	{
		return conn;
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

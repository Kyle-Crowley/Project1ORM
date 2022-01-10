# Project1ORM
Custom ORM Framework for Project 1


# What the user needs to do:
In order to access the framework

-Create the entity handler and pass in annotated classes first!

-Create the config file and set the 5 database properties needed to connect

-Create the util file that initializes an Configuration object 

-Initialize the SessionFactory with the Configuration object

-Recommended you make two methods in this file for getting a session object and closing a session object

-Annotate your models with:

	-@Entity(tableName="class_name_here")
	-@Column(columnName="field_name_here")
	-@PrimaryKeyColumn(columnName="field_name_here")
	-@ForeignKeyColumn(columnName="field_name_here")
  
To access the database

-Create a Session Object

-Initialize it with the SessionFactory in the user defined util class

-Call the member function of the session object that you want to use

	Pass in the POJO
  
-Close the session 



# What the framework will do:

To access the database

-Pass the database credentials to the Session object and connect to database

-Convert POJO to RDS equivalent variables

-Run SQL queries in methods

	-SELECT {} FROM {}
  
	-KEYWORD {optional kw} | TABLE_NAMES | KEYWORD | COLUMN_NAMES
  
-Return output to user



package com.revature.util.metamodels;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import com.revature.annotations.Column;
import com.revature.annotations.Entity;
import com.revature.annotations.ForeignKeyColumn;
import com.revature.annotations.PrimaryKeyColumn;

public class MetaModel<T>
{
	private Class<?> metaClass;
	private PrimaryKeyField primaryKeyField = null;
	private List<ColumnField> columnFields;
	private List<ForeignKeyField> foreignKeyFields;

	public static MetaModel<Class<?>> of(Class<?> metaClass)
	{

		// we check that the class we're passing through has the @Entity annotation
		if (metaClass.getAnnotation(Entity.class) == null)
		{
			throw new IllegalStateException("Cannot create MetaModel object! Provided class " + metaClass.getName()
					+ " is not annotated with @Entity");

		}
		// if so....return a new MetaModel object of the class passed through
		return new MetaModel<>(metaClass);
	}

	public MetaModel(Class<?> metaClass)
	{
		this.metaClass = metaClass;
		this.columnFields = new LinkedList<>();
		this.foreignKeyFields = new LinkedList<>();
	}

	public List<ColumnField> getColumns()
	{
		// this method returns all the properties of the class that are marked with
		// @Column annotation

		Field[] fields = metaClass.getDeclaredFields();

		// for each field within the above Field[], check if it has the Column annotation
		for (Field field : fields)
		{

			Column column = field.getAnnotation(Column.class);

			if (column != null)
			{
				// if it isn't null, instantiate a new ColumnField object
				columnFields.add(new ColumnField(field));
			}
		}

		// if columns fields are all empty 
		if (columnFields.isEmpty())
		{
			throw new RuntimeException("No columns found in: " + metaClass.getName());
		}

		return columnFields;
	}

	public PrimaryKeyField getPrimaryKey()
	{
		if(primaryKeyField != null)
		{
			return primaryKeyField;
		}
		// capture an array of its fields
		Field[] fields = metaClass.getDeclaredFields();
		// check if the primaryKeyColumn comes back as NOT null
		for (Field field : fields)
		{
			PrimaryKeyColumn primaryKey = field.getAnnotation(PrimaryKeyColumn.class);

			// IF primaryKey is NOT null, generate a PrimaryKeyField object from it
			if (primaryKey != null)
			{
				// this will capture the first and (hopefully) only primary key that exists
				return new PrimaryKeyField(field);
			}
		}

		throw new RuntimeException("Did not find a field annotated with @Id in " + metaClass.getName());
	}

	
	public List<ForeignKeyField> getForeignKeys()
	{

		Field[] fields = metaClass.getDeclaredFields();

		for (Field field : fields)
		{

			ForeignKeyColumn foreignKey = field.getAnnotation(ForeignKeyColumn.class);

			if (foreignKey != null)
			{
				foreignKeyFields.add(new ForeignKeyField(field));
			}
		}

		if (foreignKeyFields.isEmpty())
		{
			throw new RuntimeException("No foreign keys found in: " + metaClass.getName());
		}

		return foreignKeyFields;

	}
	
	public String getSimpleClassName() 
	{
		return metaClass.getSimpleName();
	}
	
	public String getClassName() {
		return metaClass.getName();
	}
	

}
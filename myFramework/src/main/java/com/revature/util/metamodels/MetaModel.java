package com.revature.util.metamodels;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
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
	private EntityField entityField;

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
		this.setMetaClass(metaClass);
		this.columnFields = new LinkedList<>();
		this.foreignKeyFields = new LinkedList<>();
	}
	
	public EntityField getEntityType()
	{
		Field[] fields = getMetaClass().getDeclaredFields();
		
		
		
		
		for (Field field : fields)
		{
			Entity column = field.getAnnotation(Entity.class);

			if (column != null)
			{
				// if it isn't null, instantiate a new ColumnField object
				entityField= new EntityField(field);
			}
			else
			{
				return null;
			}
		}
		
		return entityField;
	}

	public List<ColumnField> getColumns()
	{
		// this method returns all the properties of the class that are marked with
		// @Column annotation

		Field[] fields = getMetaClass().getDeclaredFields();
		
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
			return Collections.emptyList();
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
		Field[] fields = getMetaClass().getDeclaredFields();
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

		throw new RuntimeException("Did not find a field annotated with @Id in " + getMetaClass().getName());
	}

	
	public List<ForeignKeyField> getForeignKeys()
	{

		Field[] fields = getMetaClass().getDeclaredFields();

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
			return Collections.emptyList();
		}

		return foreignKeyFields;

	}
	
	public String getSimpleClassName() 
	{
		return getMetaClass().getSimpleName();
	}
	
	public String getClassName() {
		return getMetaClass().getName();
	}

	public Class<?> getMetaClass() {
		return metaClass;
	}

	public void setMetaClass(Class<?> metaClass) {
		this.metaClass = metaClass;
	}
	

}

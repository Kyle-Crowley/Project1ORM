package com.revature.util.metamodels;

import java.lang.reflect.Field;

import com.revature.annotations.Entity;

public class EntityField 
{
	private Field field;
	
	public String getName()
	{
		return field.getName();
	}
	
	public EntityField(Field field)
	{
		if(field.getAnnotation(Entity.class) == null) 
		{
			throw new IllegalStateException("Cannot create EntityField object! Provided field, "
					+ getName() + "is not annotated with @Entity");
		}
		this.field = field;
	}
	
	public String getColumnName() 
	{
		return field.getAnnotation(Entity.class).tableName();
	}
	
	public Class<?> getType() 
	{
		return field.getType();
	}
}

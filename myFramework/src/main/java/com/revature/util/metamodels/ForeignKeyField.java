package com.revature.util.metamodels;

import java.lang.reflect.Field;

import com.revature.annotations.ForeignKeyColumn;

public class ForeignKeyField
{
	private Field field;
	
	public ForeignKeyField(Field field)
	{
		if(field.getAnnotation(ForeignKeyColumn.class) == null) 
		{
			throw new IllegalStateException("Cannot create ColumnField object! Provided field, "
					+ getName() + "is not annotated with @JoinColumn");
		}
		this.field = field;
	}
	
	public String getName() 
	{
		return field.getName();
	}
	
	public Class<?> getType() 
	{
		return field.getType();
	}
	
	public String getColumnName() 
	{
		return field.getAnnotation(ForeignKeyColumn.class).columnName();
	}
}

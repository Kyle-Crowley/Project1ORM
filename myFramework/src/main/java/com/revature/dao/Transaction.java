package com.revature.dao;

import java.util.LinkedList;
import java.util.List;

import com.revature.reflection.ClassInspector;

public class Transaction 
{
	public Transaction()
	{
		
	}
	
	public void parseJavaType(Object obj)
	{
		
		try {
			List<Object> classVar = new LinkedList<Object>();
			classVar = ClassInspector.listPublicFields(obj.getClass());
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
}

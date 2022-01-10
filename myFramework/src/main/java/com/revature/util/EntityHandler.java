package com.revature.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.revature.util.metamodels.MetaModel;


public class EntityHandler
{
	private List<MetaModel<Class<?>>> metaModelList;
	
	public EntityHandler addAnnotatedClass(Class metaClass)
	{
		System.out.println("Adding annotated class");
		if(metaModelList == null)
			metaModelList = new LinkedList<>();
		
		metaModelList.add(MetaModel.of(metaClass));
		
		return this;
	}
	
	public List<MetaModel<Class<?>>> getMetaModels()
	{
		return (metaModelList == null) ? Collections.emptyList() : metaModelList;
	}
}

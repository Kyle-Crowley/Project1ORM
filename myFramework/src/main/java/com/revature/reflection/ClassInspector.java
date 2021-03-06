package com.revature.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ClassInspector
{
	public static void inspectClass(Class<?> metaClass) throws IllegalArgumentException, IllegalAccessException
	{
		listPublicConstructors(metaClass);
		listNonPublicConstructors(metaClass);
		listPublicFields(metaClass);
		listNonPublicFields(metaClass);
		listPublicMethods(metaClass);
		listDeclaredMethods(metaClass);
	}
	
	public static void listPublicConstructors(Class<?> metaClass)
	{
		System.out.println("Printing the public constructors of the " + metaClass.getName());
		Constructor<?>[] constructors = metaClass.getConstructors();
		for (Constructor<?> constructor : constructors) 
		{
			System.out.println("\tConstructor name: " + constructor.getName());
			System.out.println("\tConstructor param types: " + Arrays.toString(constructor.getParameterTypes()) + "\n");
			System.out.println();
		}
	}
	
	public static void listNonPublicConstructors(Class<?> metaClass)
	{
		System.out.println("Printing non-visible constructors of the " + metaClass.getName());
		Constructor<?>[] constructors = metaClass.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			// Parse modifier out
			if ((constructor.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
				continue;
			}
			System.out.println("\tConstructor name: " + constructor.getName());
			System.out.println("\tConstructor param types: " + Arrays.toString(constructor.getParameterTypes()) + "\n");
			System.out.println();
		}
	}
	
	public static List<Object> listPublicFields(Class<?> metaClass) throws IllegalArgumentException, IllegalAccessException
	{
		List<Object> classVar = new LinkedList<Object>();
		System.out.println("Printing public fields of the " + metaClass.getName());
		Field[] fields = metaClass.getDeclaredFields();
		if (fields.length == 0) 
		{
			System.out.println("There are no public fields in " + metaClass.getName());
		}
		for (Field field : fields) 
		{
			classVar.add(field.get(metaClass));
			System.out.println("\tField name: " + field.getName());
			System.out.println("\tField type: " + field.getType());
			System.out.println("\tIs field primitive? :: " + field.getType().isPrimitive());
			System.out.println("\tModifiers bit value: " + Integer.toBinaryString(field.getModifiers()) + "\n");
		}
		return classVar;
	}
	
	public static void listNonPublicFields(Class<?> metaClass)
	{
		System.out.println("Printing non-public fields of the " + metaClass.getName());
		Field[] fields = metaClass.getDeclaredFields();
		if (fields.length == 0) {
			System.out.println("\nThere are no non-public fields in " + metaClass.getName());
		}
		for (Field field : fields) {
			if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC)
				continue;
			System.out.println("\tField name: " + field.getName());
			System.out.println("\tField type: " + field.getType());
			System.out.println("\tIs field primitive?: " + field.getType().isPrimitive());
			System.out.println("\tModifiers bit value: " + Integer.toBinaryString(field.getModifiers()));
			System.out.println();
		}
	}
	
	public static void listPublicMethods(Class<?> metaClass)
	{
		System.out.println("Printing public fields of the " + metaClass.getName());
		Method[] methods = metaClass.getMethods();
		if (methods.length == 0) {
			System.out.println("There are no public fields in " + metaClass.getName());
		}
		for (Method method : methods) {
			if (method.getDeclaringClass() == Object.class)
				continue;
			System.out.println("\nMethod name: " + method.getName());
			System.out.println("\tMethod param count: " + method.getParameterCount());
			System.out.println("\tMethod declared class: " + method.getDeclaringClass());
			System.out.println("\tMethod declared annotations: " + Arrays.toString(method.getDeclaredAnnotations()));

			Parameter[] params = method.getParameters();
			for (Parameter param : params) {
				System.out.println("\t\tParameter name: " + param.getName());
				System.out.println("\t\tParameter type: " + param.getType());
				System.out.println("\t\tParameter annotations: " + Arrays.toString(param.getDeclaredAnnotations()));
			}
			System.out.println();
		}
	}
	
	public static void listDeclaredMethods(Class<?> metaClass)
	{
		System.out.println("Listing all of the declared methods of the class: " + metaClass.getName());
		Method[] methods = metaClass.getDeclaredMethods();

		if (methods.length == 0) {
			System.out.println("\tThere are no non-public methods in the class: " + metaClass.getName());
		}

		for (Method method : methods) {

			if ((method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC) {
				continue;
			}

			System.out.println("\tName: " + method.getName());
			Class<?>[] parameterTypes = method.getParameterTypes();
			System.out.println("\tModifiers bit value: " + Integer.toBinaryString(method.getModifiers()));
			System.out.println("\tDeclaring class: " + method.getDeclaringClass().getName());
			System.out.println("\tDeclared annotations: " + Arrays.toString(method.getDeclaredAnnotations()));

			System.out.println("\tParameter count: " + parameterTypes.length);
			Parameter[] params = method.getParameters();
			for (Parameter param : params) {
				System.out.println("\t\tParameter name: " + param.getName());
				System.out.println("\t\tParameter type: " + param.getType());
				System.out.println("\t\tParameter annotations: " + Arrays.toString(param.getAnnotations()));
			}
			System.out.println();
		}
	}
	
	
}

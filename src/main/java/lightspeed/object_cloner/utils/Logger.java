package lightspeed.object_cloner.utils;

import java.lang.reflect.Field;

/**
 * @author Sergei Aleshchenko
 */
public class Logger
{
	public void startCloning(Class<?> aClass) {
		System.out.println("Cloning a class:  " + aClass);
	}

	public void cloning(Field field, Class<?> aClass) {
		System.out.println("Cloning a field:  " + field + " of class " + aClass);
	}
}

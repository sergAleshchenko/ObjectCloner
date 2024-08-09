package lightspeed.object_cloner.utils;

import lightspeed.object_cloner.InstantiationStrategy;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

/**
 * @author Sergei Aleshchenko
 */
public class InstantiationUtils implements InstantiationStrategy {
	private final Objenesis	objenesis = new ObjenesisStd();

	public <T>ObjectInstantiator<T> getInstance(Class<T> c) {
		return objenesis.getInstantiatorOf(c);
	}

	private static InstantiationUtils instance = new InstantiationUtils();

	public static InstantiationUtils getInstance()
	{
		return instance;
	}
}

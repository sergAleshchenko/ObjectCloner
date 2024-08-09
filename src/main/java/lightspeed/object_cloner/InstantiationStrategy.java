package lightspeed.object_cloner;

import org.objenesis.instantiator.ObjectInstantiator;

/**
 * @author Sergei Aleshchenko
 */
public interface InstantiationStrategy {
	<T>ObjectInstantiator<T> getInstance(Class<T> c);
}

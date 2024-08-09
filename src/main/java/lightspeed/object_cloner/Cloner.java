package lightspeed.object_cloner;

import lightspeed.object_cloner.deep_cloners.*;
import lightspeed.object_cloner.shallow_cloners.*;
import lightspeed.object_cloner.utils.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sergei Aleshchenko
 */
public class Cloner {
	private final Set<Class<?>> ignored = new HashSet<>();
	private final Set<Class<?>> ignoredInstanceOf = new HashSet<>();
	private final Map<Class<?>, ShallowCloner> fastCloners = new HashMap<>();
	private Map<Object, Object> ignoredInstances;
	private Logger logger = new Logger();
	private boolean cloningEnabled = true;
	private Map<Class, DeepCloner> cloners = new ConcurrentHashMap<>();
	private static DeepCloner IGNORE_CLONER = new IgnoreClassCloner();
	private static DeepCloner NULL_CLONER = new NullClassCloner();

	public Cloner() {
		init();
	}

	private void init() {
		registerJdkClasses();
		registerCloners();
	}

	protected void registerCloners() {
		registerCloner(ArrayList.class, new ClonerArrayList());
		registerCloner(LinkedList.class, new ClonerLinkedList());
		registerCloner(HashSet.class, new ClonerHashSet());
		registerCloner(HashMap.class, new ClonerHashMap());
		registerCloner(TreeMap.class, new ClonerTreeMap());
		registerCloner(TreeSet.class, new ClonerTreeSet());
		registerCloner(LinkedHashMap.class, new ClonerLinkedHashMap());
		registerCloner(LinkedHashSet.class, new ClonerLinkedHashSet());
	}

	protected void registerJdkClasses() {
		registerImmutable(String.class);
		registerImmutable(Integer.class);
		registerImmutable(Long.class);
		registerImmutable(Boolean.class);
		registerImmutable(Class.class);
		registerImmutable(Float.class);
		registerImmutable(Double.class);
		registerImmutable(Character.class);
		registerImmutable(Byte.class);
		registerImmutable(Short.class);
		registerImmutable(Void.class);
	}

	public void registerImmutable(final Class<?>... classes) {
		for (final Class<?> aClass : classes) {
			ignored.add(aClass);
		}
	}


	public void registerCloner(final Class<?> aClass, final ShallowCloner shallowCloner) {
		if (fastCloners.containsKey(aClass)) throw new IllegalArgumentException(aClass + " already fast-cloned!");
		fastCloners.put(aClass, shallowCloner);
	}


	public <T> T deepClone(final T object) {
		if (object == null) return null;
		if (!cloningEnabled) return object;

		logger.startCloning(object.getClass());

		Map<Object, Object> clones = new ClonesMap();
		return cloneInternal(object, clones);
	}

	public <T> T cloneInternal(T object, Map<Object, Object> clones) {
		if (object == null) return null;
		if (object == this) return null;

		if (clones != null) {
			T clone = (T) clones.get(object);
			if (clone != null) {
				return clone;
			}
		}

		Class<?> aClass = object.getClass();
		DeepCloner cloner = cloners.get(aClass);
		if (cloner == null) {
			cloner = findDeepCloner(aClass);
			cloners.put(aClass, cloner);
		}
		if (cloner == IGNORE_CLONER) {
			return object;
		} else if (cloner == NULL_CLONER) {
			return null;
		}
		return cloner.deepClone(object, clones);
	}

	public DeepCloner findDeepCloner(Class<?> aClass) {
		if (ignored.contains(aClass)) {
			return IGNORE_CLONER;
		} else if (aClass.isArray()) {
			return new ArrayCloner(aClass);
		} else {
			final ShallowCloner fastCloner = fastCloners.get(aClass);
			if (fastCloner != null) {
				return new FastCloner(fastCloner);
			} else {
				for (final Class<?> iClz : ignoredInstanceOf) {
					if (iClz.isAssignableFrom(aClass)) {
						return IGNORE_CLONER;
					}
				}
			}
		}
		return new ObjectCloner(aClass);
	}

	private class ClonesMap extends IdentityHashMap<Object, Object> {
		@Override
		public Object get(Object key) {
			if (ignoredInstances != null) {
				Object o = ignoredInstances.get(key);
				if (o != null) return o;
			}
			return super.get(key);
		}
	}
}

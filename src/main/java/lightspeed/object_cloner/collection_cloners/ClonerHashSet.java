package lightspeed.object_cloner.collection_cloners;

import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.HashSet;
import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public class ClonerHashSet implements ShallowCloner {
    public Object clone(final Object object, final DeepCloner cloner, final Map<Object, Object> clones) {
		final HashSet hashSet = (HashSet) object;
		final HashSet hashSet1 = new HashSet();
		for (final Object o : hashSet)
		{
			hashSet1.add(cloner.deepClone(o, clones));
		}
		return hashSet1;
	}
}

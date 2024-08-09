package lightspeed.object_cloner.collection_cloners;

import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.LinkedHashSet;
import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public class ClonerLinkedHashSet implements ShallowCloner {
    public Object clone(final Object object, final DeepCloner cloner, final Map<Object, Object> clones) {
		final LinkedHashSet<?> linkedHashSet = (LinkedHashSet) object;
		final LinkedHashSet linkedHashSet1 = new LinkedHashSet();
		for (final Object o : linkedHashSet)
		{
			linkedHashSet1.add(cloner.deepClone(o, clones));
		}
		return linkedHashSet1;
	}
}

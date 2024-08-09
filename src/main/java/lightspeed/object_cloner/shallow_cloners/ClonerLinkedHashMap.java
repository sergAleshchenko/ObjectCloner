package lightspeed.object_cloner.shallow_cloners;

import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public class ClonerLinkedHashMap implements ShallowCloner {
	public Object clone(final Object object, final DeepCloner cloner, final Map<Object, Object> clones) {
		final LinkedHashMap<?, ?> linkedHashMap = (LinkedHashMap) object;
		final LinkedHashMap linkedHashMap1 = new LinkedHashMap();
		for (final Map.Entry e : linkedHashMap.entrySet()) {
			linkedHashMap1.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
		}
		return linkedHashMap1;
	}
}

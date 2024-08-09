package lightspeed.object_cloner.shallow_cloners;

import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public class ClonerHashMap implements ShallowCloner {
    public Object clone(final Object object, final DeepCloner cloner, final Map<Object, Object> clones) {
		final HashMap<Object, Object> hashMap = (HashMap) object;
		final HashMap hashMap1 = new HashMap();
		for (final Map.Entry e : hashMap.entrySet()) {
			hashMap1.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
		}
		return hashMap1;
	}
}

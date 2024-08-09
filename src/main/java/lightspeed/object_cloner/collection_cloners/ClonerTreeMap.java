package lightspeed.object_cloner.collection_cloners;

import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Sergei Aleshchenko
 */
public class ClonerTreeMap implements ShallowCloner {
    public Object clone(final Object object, final DeepCloner cloner, final Map<Object, Object> clones) {
		final TreeMap<Object, Object> treeMap = (TreeMap) object;
		final TreeMap treeMap1 = new TreeMap(treeMap.comparator());
		for (final Map.Entry e : treeMap.entrySet()) {
			treeMap1.put(cloner.deepClone(e.getKey(), clones), cloner.deepClone(e.getValue(), clones));
		}
		return treeMap1;
	}
}

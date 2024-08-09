package lightspeed.object_cloner.shallow_cloners;

import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.Map;
import java.util.TreeSet;

/**
 * @author Sergei Aleshchenko
 */
public class ClonerTreeSet implements ShallowCloner {
	public Object clone(Object object, DeepCloner cloner, Map<Object, Object> clones) {
		TreeSet<?> treeSet = (TreeSet<?>) object;
		TreeSet treeSet1 = new TreeSet(treeSet.comparator());
		for (Object o : treeSet) {
			treeSet1.add(cloner.deepClone(o, clones));
		}
		return treeSet1;
	}
}

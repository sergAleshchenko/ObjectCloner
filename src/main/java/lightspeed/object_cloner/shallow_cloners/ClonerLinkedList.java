package lightspeed.object_cloner.shallow_cloners;

import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.LinkedList;
import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public class ClonerLinkedList implements ShallowCloner {
    public Object clone(final Object object, final DeepCloner cloner, final Map<Object, Object> clones) {
		final LinkedList linkedList = (LinkedList) object;
		final LinkedList linkedList1 = new LinkedList();
		for (final Object o : linkedList)
		{
			linkedList1.add(cloner.deepClone(o, clones));
		}
		return linkedList1;
	}
}

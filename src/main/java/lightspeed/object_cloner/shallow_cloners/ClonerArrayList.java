package lightspeed.object_cloner.shallow_cloners;

import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public class ClonerArrayList implements ShallowCloner {
	public Object clone(final Object object, final DeepCloner cloner, final Map<Object, Object> clones) {
		ArrayList arrayList = (ArrayList) object;
		int size = arrayList.size();
		ArrayList arrayList1 = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			arrayList1.add(cloner.deepClone(arrayList.get(i), clones));
		}
		return arrayList1;
	}

}

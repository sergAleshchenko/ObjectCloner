package lightspeed.object_cloner;

import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public interface ShallowCloner {
    Object clone(Object t, DeepCloner cloner, Map<Object, Object> clones);
}

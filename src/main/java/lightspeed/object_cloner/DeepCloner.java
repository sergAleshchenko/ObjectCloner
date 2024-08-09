package lightspeed.object_cloner;

import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public interface DeepCloner {
    <T> T deepClone(final T o, final Map<Object, Object> clones);
}

package lightspeed.object_cloner.deep_cloners;

import lightspeed.object_cloner.Cloner;
import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.ShallowCloner;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sergei Aleshchenko
 */
public class FastCloner implements DeepCloner {
    private ShallowCloner shallowCloner;
    private DeepCloner cloneInternal;
    private Cloner aCloner = new Cloner();
    private DeepCloner deepCloner = this::cloneInternal;
    private Map<Class, DeepCloner> cloners = new ConcurrentHashMap<>();
    private static DeepCloner IGNORE_CLONER = new IgnoreClassCloner();
    private static DeepCloner NULL_CLONER = new NullClassCloner();

    public FastCloner(ShallowCloner shallowCloner) {
        this.shallowCloner = shallowCloner;
        this.cloneInternal = deepCloner;
    }

    public <T> T deepClone(T object, Map<Object, Object> clones) {
        T clone = (T) shallowCloner.clone(object, cloneInternal, clones);
        if (clones != null) clones.put(object, clone);
        return clone;
    }

    public <T> T cloneInternal(T object, Map<Object, Object> clones) {
        if (object == null) return null;
        if (object == this) return null;

        if (clones != null) {
            T clone = (T) clones.get(object);
            if (clone != null) {
                return clone;
            }
        }

        Class<?> aClass = object.getClass();
        DeepCloner cloner = cloners.get(aClass);
        if (cloner == null) {
            cloner = aCloner.findDeepCloner(aClass);
            cloners.put(aClass, cloner);
        }
        if (cloner == IGNORE_CLONER) {
            return object;
        } else if (cloner == NULL_CLONER) {
            return null;
        }
        return cloner.deepClone(object, clones);
    }
}


package lightspeed.object_cloner.deep_cloners;

import lightspeed.object_cloner.Cloner;
import lightspeed.object_cloner.DeepCloner;
import lightspeed.object_cloner.utils.Logger;
import java.lang.reflect.Array;
import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public class ArrayCloner implements DeepCloner {
    private boolean primitive;
    private Class<?> componentType;
    private Logger logger = new Logger();
    private Cloner cloner = new Cloner();


    public ArrayCloner(Class<?> aClass) {
        primitive = aClass.getComponentType().isPrimitive();
        componentType = aClass.getComponentType();
    }

    public <T> T deepClone(T object, Map<Object, Object> clones) {
        if (logger != null) {
            logger.startCloning(object.getClass());
        }
        int length = Array.getLength(object);
        T newInstance = (T) Array.newInstance(componentType, length);
        if (clones != null) {
            clones.put(object, newInstance);
        }
        if (primitive) {
            System.arraycopy(object, 0, newInstance, 0, length);
        } else {
            if (clones == null) {
                for (int i = 0; i < length; i++) {
                    Array.set(newInstance, i, Array.get(object, i));
                }
            } else {
                for (int i = 0; i < length; i++) {
                    Array.set(newInstance, i, cloner.cloneInternal(Array.get(object, i), clones));
                }
            }
        }
        return newInstance;
    }
}
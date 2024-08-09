package lightspeed.object_cloner.deep_cloners;

import lightspeed.object_cloner.CloningException;
import lightspeed.object_cloner.DeepCloner;

import java.util.Map;

/**
 * @author Sergei Aleshchenko
 */
public class IgnoreClassCloner implements DeepCloner {
    public <T> T deepClone(T object, Map<Object, Object> clones) {
        throw new CloningException("Error of cloning object!");
    }
}

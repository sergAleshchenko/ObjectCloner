package lightspeed.object_cloner.deep_cloners;

import lightspeed.object_cloner.*;
import lightspeed.object_cloner.utils.InstantiationUtils;
import lightspeed.object_cloner.utils.Logger;
import org.objenesis.instantiator.ObjectInstantiator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author Sergei Aleshchenko
 */
public class ObjectCloner implements DeepCloner {
    private final Field[] fields;
    private final boolean[] shouldClone;
    private final int numFields;
    private final ObjectInstantiator<?> instantiator;
    private Logger logger = new Logger();

    private boolean nullTransient = false;
    private boolean cloneSynthetics = true;
    private static final Field[] EMPTY_FIELD_ARRAY = new Field[0];

    private InstantiationStrategy instantiationStrategy;
    private boolean cloneAnonymousParent = true;

    private final Set<Class<? extends Annotation>> nullInsteadFieldAnnotations = new HashSet<>();

    private Cloner cloner = new Cloner();

    public ObjectCloner(Class<?> aClass) {
        this.instantiationStrategy = InstantiationUtils.getInstance();
        List<Field> l = new ArrayList<>();
        List<Boolean> shouldCloneList = new ArrayList<>();
        Class<?> aClass1 = aClass;
        do {
            Field[] declaredFields = aClass1.getDeclaredFields();
            for (final Field f : declaredFields) {
                int modifiers = f.getModifiers();
                boolean isStatic = Modifier.isStatic(modifiers);
                if (!isStatic) {
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }
                    if (!(nullTransient && Modifier.isTransient(modifiers)) && !isFieldNullInsteadBecauseOfAnnotation(f)) {
                        l.add(f);
                        boolean shouldClone = (cloneSynthetics || !f.isSynthetic()) && (cloneAnonymousParent || !isAnonymousParent(f));
                        shouldCloneList.add(shouldClone);
                    }
                }
            }
        } while ((aClass1 = aClass1.getSuperclass()) != Object.class && aClass1 != null);
        fields = l.toArray(EMPTY_FIELD_ARRAY);
        numFields = fields.length;
        shouldClone = new boolean[numFields];
        for (int i = 0; i < shouldCloneList.size(); i++) {
            shouldClone[i] = shouldCloneList.get(i);
        }
        instantiator = instantiationStrategy.getInstance(aClass);
    }

    private boolean isFieldNullInsteadBecauseOfAnnotation(Field field) {
        if(!nullInsteadFieldAnnotations.isEmpty()) {
            for (Annotation annotation : field.getAnnotations()) {
                boolean isAnnotatedWithNullInsteadAnnotation =
                        nullInsteadFieldAnnotations.contains(annotation.annotationType());
                if (isAnnotatedWithNullInsteadAnnotation) {
                    return true;
                }
            }
        }
        return false;
    }

    public <T> T deepClone(T object, Map<Object, Object> clones) {
        try {
            if (logger != null) {
                logger.startCloning(object.getClass());
            }

            T newInstance = (T) instantiator.newInstance();

            if (clones != null) {
                clones.put(object, newInstance);
                for (int i = 0; i < numFields; i++) {
                    Field field = fields[i];
                    Object fieldObject = field.get(object);
                    Object fieldObjectClone = shouldClone[i] ? cloner.cloneInternal(fieldObject, clones) : fieldObject;
                    field.set(newInstance, fieldObjectClone);
                    if (logger != null && fieldObjectClone != fieldObject) {
                        logger.cloning(field, object.getClass());
                    }
                }
            } else {
                for (int i = 0; i < numFields; i++) {
                    Field field = fields[i];
                    field.set(newInstance, field.get(object));
                }
            }
            return newInstance;
        } catch (IllegalAccessException e) {
            throw new CloningException("Error cloning!");
        }
    }

    private boolean isAnonymousParent(final Field field) {
        return "this$0".equals(field.getName());
    }
}

package tarkhana.objectdiffer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class ObjectDiffer {

    public ObjectDiffer() {}

    public <T> DiffNode getDiff(T oldO, T newO) throws IllegalAccessException {
        if (oldO == null && newO == null) {
            return null;
        }
        return getDiff(oldO, newO, "root");
    }

    private <T> DiffNode getDiff(T oldO, T newO, String name) throws IllegalAccessException {
        if (oldO == null && newO == null) {
            return new DiffNode.DiffNodeBuilder()
                    .withName(name)
                    .withState(DiffState.UNCHANGED)
                    .withOldValue(null)
                    .withNewValue(null)
                    .withChildren(new ArrayList<>())
                    .build();
        }

        T toUse = newO;
        DiffState state = null;
        List<DiffNode> childDiffs = new ArrayList<>();

        if (oldO == null) {
            state = DiffState.CREATED;
        } else if (newO == null) {
            state = DiffState.REMOVED;
            toUse = oldO;
        }

        Class<?> clazz = toUse.getClass();
        if (clazz.isPrimitive() ||
            TypeUtils.isPrimitiveWrapper(clazz) ||
            clazz.isEnum() ||
            clazz.isAssignableFrom(String.class)) {
            if (state == null && !oldO.equals(newO)) {
                state = DiffState.CHANGED;
            }
        } else {
            Set<String> fields = new HashSet<>();
            Map<String, Object> oldValues = getFieldValues(oldO, fields);
            Map<String, Object> newValues = getFieldValues(newO, fields);
            for (String field : fields) {
                Object oldV = oldValues.get(field);
                Object newV = newValues.get(field);
                DiffNode childDiff = getDiff(
                    oldV,
                    newV,
                    field
                );
                if (state == null && childDiff.getState() != DiffState.UNCHANGED) {
                    state = DiffState.CHANGED;
                }
                childDiffs.add(childDiff);
            }
        }

        return new DiffNode.DiffNodeBuilder()
                .withName(name)
                .withState(state == null ? DiffState.UNCHANGED : state)
                .withOldValue(oldO)
                .withNewValue(newO)
                .withChildren(childDiffs)
                .build();
    }

    private <T> Map<String, Object> getFieldValues(T o, Set<String> fields) throws IllegalAccessException {
        if (o == null) {
            return new HashMap<>();
        }
        Map<String, Object> result = new HashMap<>();
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            result.put(field.getName(), field.get(o));
            fields.add(field.getName());
        }
        return result;
    }

    public static final class ObjectDiffBuilder {

        public ObjectDiffer build() {
            return new ObjectDiffer();
        }

    }

}

package tarkhana.objectdiffer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
            for (Field field : toUse.getClass().getDeclaredFields()) {
                DiffNode childDiff = getDiff(
                        oldO == null ? null : field.get(oldO),
                        newO == null ? null : field.get(newO),
                        field.getName()
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

    public static final class ObjectDiffBuilder {

        public ObjectDiffer build() {
            return new ObjectDiffer();
        }

    }

}

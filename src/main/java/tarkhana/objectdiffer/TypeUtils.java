package tarkhana.objectdiffer;

public class TypeUtils {

    public static boolean isPrimitiveWrapper(Class<?> clazz) {
        return clazz == Integer.class ||
            clazz == Long.class ||
            clazz == Float.class ||
            clazz == Double.class ||
            clazz == Boolean.class ||
            clazz == Character.class ||
            clazz == Byte.class ||
            clazz == Short.class;
    }

}

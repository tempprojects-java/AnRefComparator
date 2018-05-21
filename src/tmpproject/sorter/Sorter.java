package tmpproject.sorter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sorter {
    public static Class get(Class<?> gType) {
        String packcageName = Sorter.class.getPackage().getName();
        return getActiveComparatorClass(getComparatorClasses(packcageName), gType);
    }

    private static Class getActiveComparatorClass(List<Class> clss, Class<?> gType) {
        Class result = null;

        for (Class cls : clss) {
            Type[] genericInterfaces = cls.getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                if (result != null) {
                    break;
                }
                if (!(genericInterface instanceof ParameterizedType)) {
                    continue;
                }

                Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
                for (Type genericType : genericTypes) {
                    if (gType.getName().equals(genericType.getTypeName()) && cls.getAnnotation(Active.class) != null) {
                        result = cls;
                        break;
                    }
                }
            }
        }

        return result;
    }

    private static List<Class> getComparatorClasses(String packcageName) {
        List<Class> result = new ArrayList<>();

        try {
            List<Class> clss = PackageTools.getClasses(packcageName);
            for (Class cls : clss) {
                if (Comparator.class.isAssignableFrom(cls)) {
                    result.add(cls);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }
}

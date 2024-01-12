package gov.babalar.utils;

import gov.babalar.Myth;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * ----------
 * 9/1/2023
 * 5:06 PM
 * ----------
 **/
public class ReflectionUtils {
    public static Method[] getAllMethods(Class<?> clazz)
    {
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            block:
            {
                if(clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals("java.lang.Object")) {
                    for (Method declaredMethod : clazz.getSuperclass().getDeclaredMethods()) {
                        if (method.toString().equals(declaredMethod.toString()))
                            break block;
                    }
                }
                methods.add(method);
            }
    }
        return methods.toArray(new Method[0]);
    }
    public static void changeValue(Field f , Object instance , Object value)

    {
        try {
            f.setAccessible(true);
            Field modifiersField = Field.class
                    .getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(f, f.getModifiers()
                    & ~Modifier.FINAL);
            f.set(instance, value);
        }catch (Exception e) {
            e.printStackTrace();
            Myth.logger.logException(e);
        }
    }

    public static Object getValue(Field f , Object instance)

    {
        try {
            f.setAccessible(true);
            Field modifiersField = Field.class
                    .getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(f, f.getModifiers()
                    & ~Modifier.FINAL);
            return f.get(instance);
        }catch (Exception e) {
            e.printStackTrace();
            Myth.logger.logException(e);
        }
        return null;
    }

    public static Method[] getAllMethods1(Class<?> clazz)
    {
        ArrayList<Method> methods = new ArrayList<>();
        for (Method method : clazz.getDeclaredMethods()) {
            block:
            {
                if(clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals("java.lang.Object")) {
                    for (Method declaredMethod : clazz.getSuperclass().getDeclaredMethods()) {
                        if (method.toString().equals(declaredMethod.toString()))
                            break block;
                    }
                }
                methods.add(method);
            }
        }
        if(clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals("java.lang.Object"))
            for (Method method : clazz.getSuperclass().getDeclaredMethods()) {
                block:
                {
                    methods.add(method);
                }
            }
        return methods.toArray(new Method[0]);
    }

    public static Field[] getAllFields1(Class<?> clazz)
    {
        ArrayList<Field> fields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            block:
            {
                if(clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals("java.lang.Object")) {
                    for (Field declaredField : clazz.getSuperclass().getDeclaredFields()) {
                        if (field.toString().equals(declaredField.toString()))
                            break block;
                    }
                }
                fields.add(field);
            }
        }
        if(clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals("java.lang.Object"))
            for (Field field : clazz.getSuperclass().getDeclaredFields()) {
                block:
                {
                    fields.add(field);
                }
            }
        return fields.toArray(new Field[0]);
    }

    public static Field[] getAllFields(Class<?> clazz)
    {

        ArrayList<Field> fields = new ArrayList<>();
        try {
            for (Field field : clazz.getDeclaredFields()) {
                block:
                {
                    if(clazz.getSuperclass() != null && !clazz.getSuperclass().getName().equals("java.lang.Object")) {
                        for (Field declaredField : clazz.getSuperclass().getDeclaredFields()) {
                            if (field.toString().equals(declaredField.toString()))
                                break block;
                        }
                    }
                    fields.add(field);
                }
            }
        }catch (Exception e)
        {
            Myth.logger.logException(e);
            return clazz.getDeclaredFields();
        }
        return fields.toArray(new Field[0]);
    }

}

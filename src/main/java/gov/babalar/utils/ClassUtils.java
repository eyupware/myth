package gov.babalar.utils;

import gov.babalar.Main;
import gov.babalar.Myth;
import sun.misc.Unsafe;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**
 * ----------
 * 9/1/2023
 * 1:06 AM
 * ----------
 **/
public class ClassUtils {
    public static ArrayList<Class> getClasses(ClassLoader classLoader , boolean filterZelix) throws Exception {
        Field f = ClassLoader.class.getDeclaredField("classes");
        f.setAccessible(true);
        if(filterZelix)
        {
            ArrayList<Class> allClasses = new ArrayList<>((Vector<Class>) f.get(classLoader));
            ArrayList<Class> filtered = new ArrayList<>();
            for (Class aClass : allClasses) {
                if(aClass.getSimpleName().length() < 3 || aClass.getName().contains("minecraft"))
                    filtered.add(aClass);

            }
            return filtered;
        }
        return new ArrayList<>((Vector<Class>) f.get(classLoader));
    }


    public static Class<?> defineClass(Class<?> targetClass) throws Exception
    {
        byte[] bytes = getClazzBytes(targetClass);
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        return unsafe.defineClass(targetClass.getName(), bytes,0, bytes.length , Main.currClassLoader , null);
    }

    public static byte[] getClazzBytes(Class<?> clazz)
    {
        try {
            InputStream classStream = Main.class.getClassLoader().getResourceAsStream(clazz.getName().replace('.', '/') + ".class");
            int qwe;
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((qwe = classStream.read()) != -1) {
                baos.write(qwe);
            }
            return baos.toByteArray();
        }catch (Exception e)
        {
            try {
                //CUSTOM CLASS LOADER ICIN LOAD SISTEMI ELLEMEYIN
                for (Field declaredField : Main.class.getClassLoader().getClass().getDeclaredFields()) {
                    if(declaredField.toString().contains("java.util.Map") && declaredField.getName().equals("classes"))
                    {
                        declaredField.setAccessible(true);
                        Map<String, byte[]> clazzez = (Map<String, byte[]>) declaredField.get(Main.class.getClassLoader());
                        return clazzez.get(clazz.getName());
                    }
                }
                //return baos.toByteArray();
            }catch (Exception ee)
            {

                ee.printStackTrace();
                Myth.logger.logException(ee);
                return null;
            }
        }
        return null;
    }

    public static ClassLoader currentThreadClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
    public static String getDetailStringify(Object o) throws IllegalAccessException {
        Class<?> objectClass = o.getClass();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(objectClass.getName());
        stringBuilder.append(" ");
        for (Field field : ReflectionUtils.getAllFields(objectClass)) {
            field.setAccessible(true);
            stringBuilder.append(field).append(" ==").append(field.get(o)).append(" &&").append("\n");
        }
        return stringBuilder.toString();
    }
}
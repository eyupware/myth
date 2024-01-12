package gov.babalar.matchers;

import gov.babalar.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * ----------
 * 9/1/2023
 * 12:39 AM
 * ----------
 **/
public class SimpleClassMatcher {
    public MethodFilter[] mFilters;
    public FieldFilter[] fFilters;

    public int distanceToObjectSuperClass;

    public String clazzPackage;

    public SimpleClassMatcher(MethodFilter[] mFilters, FieldFilter[] fFilters, int distanceToObjectSuperClass, String clazzPackage) {
        this.mFilters = mFilters;
        this.fFilters = fFilters;
        this.distanceToObjectSuperClass = distanceToObjectSuperClass;
        this.clazzPackage = clazzPackage;
    }

    public Class<?>[] matchFrom(Class<?>... classes) {
        ArrayList <Class<?>> toReturn = new ArrayList <>();
        for (Class <?> aClass : classes) {
            checkBlock: {
                if (!clazzPackage.equalsIgnoreCase("IGNORED") && !aClass.getName().contains(clazzPackage))
                    continue;
                if (distanceToObjectSuperClass != -1 && getDistanceToObjectSuperClass(aClass) != distanceToObjectSuperClass)
                    continue;
                ArrayList <Method> matchedMethods = new ArrayList <>();
                for (MethodFilter mFilter: mFilters) {
                    boolean matched = false;
                    for (Method method: ReflectionUtils.getAllMethods(aClass)) {
                        if (matchedMethods.contains(method))
                            continue;
                        if (!mFilter.isMatchWith(method))
                            continue;
                        matchedMethods.add(method);
                        matched = true;
                        break;
                    }
                    if (!matched)
                        break checkBlock;
                }
                ArrayList <Field> matchedFields = new ArrayList<>();
                for (FieldFilter mFilter: fFilters) {
                    boolean matched = false;
                    for (Field field: ReflectionUtils.getAllFields(aClass)) {
                        if (matchedFields.contains(field))
                            continue;
                        if (!mFilter.isMatchWith(field))
                            continue;
                        matchedFields.add(field);
                        matched = true;
                        break;
                    }
                    if (!matched)
                        break checkBlock;
                }
                toReturn.add(aClass);
            }
        }
        return toReturn.toArray(new Class <?> [0]);
    }
    public int getDistanceToObjectSuperClass(Class<?> clazz)
    {
        int i = 0;
        Class<?> temp = clazz;
        while(!temp.getName().equalsIgnoreCase("java.lang.Object"))
        {
            temp = temp.getSuperclass();
            ++i;
        }
        return i;
    }
}

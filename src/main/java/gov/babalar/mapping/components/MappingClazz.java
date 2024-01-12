package gov.babalar.mapping.components;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * ----------
 * 9/4/2023
 * 7:54 PM
 * ----------
 **/
public abstract class MappingClazz {
    private Class<?> clazz;
    private HashMap<String , Field> fields;
    private HashMap<String , Method> methods;

    public MappingClazz()
    {
        fields = new HashMap<>();
        methods = new HashMap<>();
        setUp();
        generateFields();
    }

    public abstract void setUp();
    public abstract void generateFields();

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public HashMap<String, Field> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Field> fields) {
        this.fields = fields;
    }

    public HashMap<String, Method> getMethods() {
        return methods;
    }

    public void setMethods(HashMap<String, Method> methods) {
        this.methods = methods;
    }
}

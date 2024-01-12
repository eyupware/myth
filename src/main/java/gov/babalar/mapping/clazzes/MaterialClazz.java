package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MaterialClazz extends MappingClazz {


    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private boolean"),
                        new FieldFilter("private boolean"),
                        new FieldFilter("private boolean"),
                        new FieldFilter("private boolean"),
                        new FieldFilter("private int"),
                        new FieldFilter("private boolean"),
                },
                -1,
                "net.minecraft"
        );
        Class<?>[] clazz = classMatcher.matchFrom(Myth.classes);
        int count = 0;
        for (Class<?> aClass : clazz) {
            for (Field field : ReflectionUtils.getAllFields(aClass)) {
                if(count > 14)
                {
                    setClazz(aClass);
                    break;
                }
                if(field.toString().contains("public static final") && field.getType().equals(aClass))
                {
                    count++;
                }
            }
        }
    }

    @Override
    public void generateFields() {

    }
}

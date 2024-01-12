package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;

import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ContainerClazz extends MappingClazz {
    @Override
    public void setUp() {
        Class<?>[] abstractClasses = Arrays.stream(Myth.classes).filter(aClass -> Modifier.isAbstract(aClass.getModifiers())).toArray(Class[]::new);
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("public java.util.List"),
                        new FieldFilter("public java.util.List"),
                        new FieldFilter("public int"),
                        new FieldFilter("private short"),
                        new FieldFilter("private int"),
                        new FieldFilter("private int"),
                        new FieldFilter("private final java.util.Set"),
                        new FieldFilter("protected java.util.List"),
                        new FieldFilter("private java.util.Set"),
                },
                -1,
                "net.minecraft"
        );
        Class<?>[] clazzes = classMatcher.matchFrom(abstractClasses);
        setClazz(clazzes[0]);
    }

    @Override
    public void generateFields() {

    }
}

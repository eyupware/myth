package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ContainerChestClazz extends MappingClazz {

    @Override
    public void setUp() {
        Class<?>[] abstractClasses = Arrays.stream(Myth.classes)
                .filter(aClass -> aClass.getSuperclass() != null)
                .filter(aClass -> aClass.getSuperclass().equals(MainMapping.map.get("Container").getClazz()))
                .toArray(Class[]::new);
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{
                        new MethodFilter(String.format("public void (%s)", MainMapping.map.get("EntityPlayer").getClazz().getName()))
                },
                new FieldFilter[]{
                        new FieldFilter("private int"),
                },
                -1,
                "net.minecraft"
        );
        Class<?>[] matchedClasses = classMatcher.matchFrom(abstractClasses);
        setClazz(matchedClasses[0]);
    }

    @Override
    public void generateFields() {

    }
}
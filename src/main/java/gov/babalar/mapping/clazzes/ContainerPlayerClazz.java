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
import java.util.Arrays;

public class ContainerPlayerClazz extends MappingClazz {

    @Override
    public void setUp() {
        Class<?>[] abstractClasses = Arrays.stream(Myth.classes)
                .filter(aClass -> aClass.getSuperclass() != null)
                .filter(aClass -> aClass.getSuperclass().equals(MainMapping.map.get("Container").getClazz()))
                .toArray(Class[]::new);
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{
                        new MethodFilter(String.format("public void (%s)", MainMapping.map.get("EntityPlayer").getClazz().getName())),
                        new MethodFilter(String.format("public boolean (%s)", MainMapping.map.get("EntityPlayer").getClazz().getName()))
                },
                new FieldFilter[]{
                        new FieldFilter("public boolean"),
                        new FieldFilter(String.format("private final %s", MainMapping.map.get("EntityPlayer").getClazz().getName())),
                },
                -1,
                "net.minecraft"
        );
        Class<?>[] matchedClasses = classMatcher.matchFrom(abstractClasses);
        for (Class<?> matchedClass : matchedClasses) {
            Myth.logger.log("\n");
            for (Field field : ReflectionUtils.getAllFields(matchedClass)) {
                Myth.logger.log("ContainerPlayer field: " + field.toString());
            }
            for (Method method : ReflectionUtils.getAllMethods(matchedClass)) {
                Myth.logger.log("ContainerPlayer method: " + method.toString());
            }
            Myth.logger.log("\n");
        }
        Myth.logger.log("ContainerPlayer length: " + matchedClasses.length);
        setClazz(matchedClasses[0]);
    }

    @Override
    public void generateFields() {

    }
}
package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;

import java.util.Arrays;

public class Vec3Clazz extends MappingClazz {


    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{
                        new MethodFilter("public double"),
                        new MethodFilter("public double"),
                        new MethodFilter("public double"),
                        new MethodFilter("public double"),
                        new MethodFilter("public java.lang.String"),
                },
                new FieldFilter[]{
                        new FieldFilter("public final double"),
                        new FieldFilter("public final double"),
                        new FieldFilter("public final double"),
                },
                -1,
                "net.minecraft"

        );
        Class<?>[] matchedClasses = classMatcher.matchFrom(Myth.classes);
        Class<?> vec3CLASS = Arrays.stream(matchedClasses).filter(aClass -> Arrays.stream(aClass.getConstructors()).anyMatch(constructor -> constructor.getParameterCount() == 3 && constructor.getParameterTypes()[0].equals(double.class))).findFirst().orElse(null);
        setClazz(vec3CLASS);
    }

    @Override
    public void generateFields() {

    }
}

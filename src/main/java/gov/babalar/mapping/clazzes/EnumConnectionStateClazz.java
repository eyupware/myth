package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;

import java.util.Arrays;

public class EnumConnectionStateClazz extends MappingClazz {


    @Override
    public void setUp() {
        Class<?>[] enumClasses = Arrays.stream(Myth.classes).filter(Class::isEnum).toArray(Class[]::new);
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("CLIENTBOUND"),
                        new FieldFilter("SERVERBOUND")
                },
                -1,
                "net.minecraft"

        );
        Class<?> clazz = classMatcher.matchFrom(enumClasses)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {

    }


}

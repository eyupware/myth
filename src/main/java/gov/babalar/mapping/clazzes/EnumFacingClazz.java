package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.util.Arrays;

public class EnumFacingClazz extends MappingClazz {


    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("DOWN"),
                        new FieldFilter("UP"),
                        new FieldFilter("NORTH"),
                        new FieldFilter("SOUTH"),
                        new FieldFilter("WEST"),
                        new FieldFilter("EAST"),
                },
                -1,
                "net.minecraft"

        );
        Class<?>[] matchedClasses = classMatcher.matchFrom(Myth.classes);
        setClazz(matchedClasses[0]);
    }

    @Override
    public void generateFields() {
        this.getFields().put("DOWN", Arrays.stream(ReflectionUtils.getAllFields(this.getClazz())).filter(field -> field.toString().contains("DOWN")).findFirst().orElse(null));
        this.getFields().put("UP", Arrays.stream(ReflectionUtils.getAllFields(this.getClazz())).filter(field -> field.toString().contains("UP")).findFirst().orElse(null));
        this.getFields().put("NORTH", Arrays.stream(ReflectionUtils.getAllFields(this.getClazz())).filter(field -> field.toString().contains("NORTH")).findFirst().orElse(null));
        this.getFields().put("SOUTH", Arrays.stream(ReflectionUtils.getAllFields(this.getClazz())).filter(field -> field.toString().contains("SOUTH")).findFirst().orElse(null));
        this.getFields().put("WEST", Arrays.stream(ReflectionUtils.getAllFields(this.getClazz())).filter(field -> field.toString().contains("WEST")).findFirst().orElse(null));
        this.getFields().put("EAST", Arrays.stream(ReflectionUtils.getAllFields(this.getClazz())).filter(field -> field.toString().contains("EAST")).findFirst().orElse(null));

    }
}

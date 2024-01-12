package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

public class BlockClazz extends MappingClazz {


    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("protected boolean"),
                        new FieldFilter("protected int"),
                        new FieldFilter("protected boolean"),
                        new FieldFilter("protected int"),
                        new FieldFilter("protected boolean"),
                        new FieldFilter("protected float"),
                        new FieldFilter("protected float"),
                        new FieldFilter("protected boolean"),
                        new FieldFilter("protected boolean"),
                        new FieldFilter("protected boolean"),
                        new FieldFilter("protected double"),
                        new FieldFilter("protected double"),
                        new FieldFilter("protected double"),
                        new FieldFilter("protected double"),
                        new FieldFilter("protected double"),
                        new FieldFilter("protected double"),
                        new FieldFilter("public float"),
                        new FieldFilter("public float"),
                        new FieldFilter("private java.lang.String"),
                },
                -1,
                "net.minecraft"
        );
        Class<?>[] clazz = classMatcher.matchFrom(Myth.classes);
        setClazz(clazz[0]);
    }

    @Override
    public void generateFields() {
        Method[] possibleMethods = Arrays.stream(ReflectionUtils.getAllMethods(this.getClazz()))
                .filter(method -> method.getReturnType().equals(boolean.class))
                .filter(method -> method.getParameterCount() == 3)
                .filter(method -> method.getParameterTypes()[1].equals(MainMapping.map.get("BlockPos").getClazz()))
                .filter(method -> method.getParameterTypes()[2].equals(MainMapping.map.get("EnumFacing").getClazz()))
                .toArray(Method[]::new);
        for (Method possibleMethod : possibleMethods) {
            if(this.getMethods().get("isBlockSolid") == null)
            {
                this.getMethods().put("isBlockSolid", possibleMethod);
            } else {
                this.getMethods().put("isBlockSolid1", possibleMethod);
            }
        }
    }
}

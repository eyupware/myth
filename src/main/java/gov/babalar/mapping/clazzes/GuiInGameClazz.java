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
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuiInGameClazz extends MappingClazz {


    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private final"),
                        new FieldFilter("private final"),
                        new FieldFilter("private long"),
                        new FieldFilter("private long"),
                        new FieldFilter("private int"),
                        new FieldFilter("private int"),
                        new FieldFilter("private int"),
                        new FieldFilter("private java.lang.String"),
                        new FieldFilter("private java.lang.String"),
                        new FieldFilter("private final java.util.Random")
                },
                -1,
                "net.minecraft.client"
        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
        /*SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private java.lang.String"),
                        new FieldFilter("private java.lang.String"),
                        new FieldFilter("private java.lang.String"),
                        new FieldFilter("private final java.util.Random")
                },
                -1,
                "net.minecraft.client"
        );
        Class<?>[] classes = Arrays.stream(classMatcher.matchFrom(Myth.classes)).filter(clazzz ->
                {
                    int pfC = 0;
                    for (Field field : clazzz.getFields()) {
                        if(field.toString().contains("public float"))
                        {
                            pfC++;
                        }
                    }
                    return pfC == 1;
                }
        ).toArray(Class<?>[]::new);
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);*/
    }

    @Override
    public void generateFields() {
        try {
            for (Method method : getClazz().getMethods()) {
                if(method.toString().contains("public void") && method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(float.class))
                {
                    getMethods().put("renderOverlay" , method);
                }
            }
        }catch (Exception e) {
            Myth.logger.logException(e);
        }
    }
}

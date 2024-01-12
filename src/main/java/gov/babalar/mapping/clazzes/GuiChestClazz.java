package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ClassUtils;
import gov.babalar.utils.ReflectionUtils;
import sun.misc.Ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;

public class GuiChestClazz extends MappingClazz {


    @Override
    public void setUp() {
        try {
            SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                    new MethodFilter[]{},
                    new FieldFilter[]{
                            new FieldFilter("private static final"),
                            new FieldFilter("private int"),
                    },
                    -1,
                    "net.minecraft.client"
            );
            Class<?>[] classes = classMatcher.matchFrom(Myth.classes);
            for (Class<?> aClass : classes) {
                for (Field allField : ReflectionUtils.getAllFields(aClass)) {
                    allField.setAccessible(true);
                    if(Modifier.isFinal(allField.getModifiers()))
                    {
                        if (ClassUtils.getDetailStringify(Objects.requireNonNull(ReflectionUtils.getValue(allField, null))).contains("generic_54.png")) {
                            setClazz(aClass);
                            break;
                        }
                    }
                }
            }
        }catch (Exception e)
        {
            Myth.logger.logException(e);
        }
        //setClazz(clazz);
    }

    @Override
    public void generateFields() {
    }
}

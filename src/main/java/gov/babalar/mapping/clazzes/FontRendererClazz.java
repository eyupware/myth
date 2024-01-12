package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ----------
 * 9/19/2023
 * 3:53 PM
 * ----------
 **/
public class FontRendererClazz extends MappingClazz {

    @Override
    public void setUp() {
        for (Field field : ReflectionUtils.getAllFields(MainMapping.map.get("Minecraft").getClazz())) {
            try {
                int pIntCount = 0;
                Field lastPint = null;
                for (Field field1 : field.getType().getFields()) {
                    if (field1.toString().contains("public int")) {
                        pIntCount++;
                        lastPint = field1;
                    }
                }
                if (pIntCount != 1)
                    continue;
                field.setAccessible(true);
                lastPint.setAccessible(true);
                int i = lastPint.getInt(field.get(Mapper.getMinecraft()));
                if (i == 9) {
                    setClazz(field.getType());
                }
            } catch (Exception e) {
                Myth.logger.logException(e);
            }
        }
    }

    @Override
    public void generateFields() {
        getMethods().put("drawString", Arrays.stream(ReflectionUtils.getAllMethods(getClazz())).filter(method ->
                method.toString().contains("public int") && method.getParameterCount() == 4 && method.getParameterTypes()[0].equals(String.class) && method.getParameterTypes()[1].equals(int.class) && method.getParameterTypes()[2].equals(int.class) && method.getParameterTypes()[3].equals(int.class)
        ).findFirst().orElse(null));
        getMethods().put("drawStringWithShadow", Arrays.stream(ReflectionUtils.getAllMethods(getClazz())).filter(method ->
                method.toString().contains("public int") && method.getParameterCount() == 5 && method.getParameterTypes()[0].equals(String.class) && method.getParameterTypes()[1].equals(float.class) && method.getParameterTypes()[2].equals(float.class) && method.getParameterTypes()[3].equals(int.class) && method.getParameterTypes()[4].equals(boolean.class)
        ).findFirst().orElse(null));
    }
}
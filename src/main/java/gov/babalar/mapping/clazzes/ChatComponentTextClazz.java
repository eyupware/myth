package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ChatComponentTextClazz extends MappingClazz {


    @Override
    public void setUp() {
        Class<?>[] babalarClassess = Arrays.stream(Myth.classes)
                .filter(aClass -> aClass.getSuperclass() != null && aClass.getSuperclass().getInterfaces().length == 1)
                .filter(aClass -> aClass.getSuperclass().getInterfaces()[0].toString().contains(MainMapping.map.get("IChatComponent").getClazz().toString()))
                .toArray(Class[]::new);

        for (Class<?> classS : babalarClassess) {
            for (Field field : ReflectionUtils.getAllFields(classS)) {
                if(field.toString().contains("private final java.lang.String"))
                {
                    setClazz(classS);
                }
            }
        }

    }

    @Override
    public void generateFields() {

    }
}

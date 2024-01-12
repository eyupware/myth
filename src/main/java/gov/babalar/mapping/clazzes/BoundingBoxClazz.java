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

public class BoundingBoxClazz extends MappingClazz {

    @Override
    public void setUp() {

        for (Field entity : ReflectionUtils.getAllFields(MainMapping.map.get("Entity").getClazz())) {
            if(entity.toString().contains("private static final"))
            {
                if(entity.toString().contains("String"))
                    continue;
                setClazz(entity.getType());
            }
        }
        //setClazz(interfaceClasses[0]);
    }

    @Override
    public void generateFields() {
      //  this.getMethods().put("addChatMessage", Arrays.stream(ReflectionUtils.getAllMethods(MainMapping.map.get("EntityPlayerSP").getClazz())).filter(method -> method.toString().contains("public void"))
      //          .filter(method -> method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(this.getClazz())).findFirst().orElse(null));
    }
}

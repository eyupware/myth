package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * ----------
 * 9/5/2023
 * 1:34 AM
 * ----------
 **/
public class GuiScreenClazz extends MappingClazz {
    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter(String.format("protected %s" , MainMapping.map.get("Minecraft").getClazz().getName())),
                        new FieldFilter("private static final org.apache.logging.log4j.Logger"),
                        new FieldFilter("private java.net.URI")
                },
                -1,
                "net.minecraft.client"
        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {
        for (Method method : ReflectionUtils.getAllMethods(this.getClazz())) {
            if(method.getParameterCount() == 3 && method.getParameters()[0].getType() == int.class && method.getParameters()[1].getType() == int.class && method.getParameters()[2].getType() == float.class)
                getMethods().put("drawScreen", method);
        }
    }
}
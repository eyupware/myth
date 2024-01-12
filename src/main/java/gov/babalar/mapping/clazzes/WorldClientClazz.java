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
import java.util.Arrays;

public class WorldClientClazz extends MappingClazz {

    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private final java.util.Set"),
                        new FieldFilter("private final java.util.Set"),
                        new FieldFilter("private final java.util.Set"),
                },
                -1,
                "net.minecraft.client"

        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {
        try {
            this.getFields().put("theWorld", Arrays.stream(ReflectionUtils.getAllFields(MainMapping.map.get("Minecraft").getClazz())).filter(field -> field.getType().equals(this.getClazz())).findFirst().orElse(null));
            Object theWorld = this.getFields().get("theWorld").get(Mapper.getMinecraft());
            for (Field field : ReflectionUtils.getAllFields(theWorld.getClass().getSuperclass())) {
                if(field.toString().contains("List") && field.toString().contains("public final") && field.toGenericString().contains(MainMapping.map.get("EntityPlayer").getClazz().getName()))
                {
                    this.getFields().put("entityList", field);
                    break;
                }
            }
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
    }
}

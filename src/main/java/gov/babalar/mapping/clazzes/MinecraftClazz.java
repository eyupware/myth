package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.util.Arrays;

/**
 * ----------
 * 9/4/2023
 * 8:31 PM
 * ----------
 **/
public class MinecraftClazz extends MappingClazz {
    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private final com.mojang.authlib.minecraft.MinecraftSessionService"),
                        new FieldFilter("private final com.mojang.authlib.properties.PropertyMap"),
                        new FieldFilter("private final java.lang.Thread")
                },
                -1,
                "net.minecraft.client"
        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {
        this.getMethods().put("getMinecraft", Arrays.stream(ReflectionUtils.getAllMethods(this.getClazz())).filter(method -> method.getReturnType().equals(this.getClazz())).findFirst().orElse(null));
        this.getFields().put("thePlayer", Arrays.stream(ReflectionUtils.getAllFields(this.getClazz())).filter(field -> field.getType().equals(MainMapping.map.get("EntityPlayerSP").getClazz())).findFirst().orElse(null));
        //this.getMethods().put("addChatMessage", Arrays.stream(ReflectionUtils.getAllMethods(this.getClazz())).filter(method -> method.toString().contains("public void") && method.toString().contains(String.format("(%s)", "java.lang.String"))).findFirst().orElse(null));

    }
}

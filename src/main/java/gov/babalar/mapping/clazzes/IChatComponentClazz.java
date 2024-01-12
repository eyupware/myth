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

public class IChatComponentClazz extends MappingClazz {

    @Override
    public void setUp() {
        Class<?>[] interfaceClasses = Arrays.stream(Myth.classes)
                .filter(Class::isInterface)
                .filter(aClass -> aClass.getInterfaces().length == 1)
                .filter(aClass -> aClass.getInterfaces()[0].toString().contains("Iterable")).toArray(Class[]::new);

        for (Class<?> interfaceClass : interfaceClasses) {
            for (Method method : ReflectionUtils.getAllMethods(interfaceClass)) {
                if(method.getReturnType().equals(interfaceClass))
                {
                    setClazz(interfaceClass);
                    break;
                }
            }
        }
        /*SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private final com.mojang.authlib.minecraft.MinecraftSessionService"),
                },
                -1,
                "IGNORED"
        );
        Class<?> clazz = classMatcher.matchFrom(interfaceClasses)[0];*/
        //setClazz(interfaceClasses[0]);
    }

    @Override
    public void generateFields() {
        this.getMethods().put("addChatMessage", Arrays.stream(ReflectionUtils.getAllMethods(MainMapping.map.get("EntityPlayerSP").getClazz())).filter(method -> method.toString().contains("public void"))
                .filter(method -> method.getParameterCount() == 1 && method.getParameterTypes()[0].equals(this.getClazz())).findFirst().orElse(null));
    }
}

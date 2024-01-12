package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PacketBufferClazz extends MappingClazz {

    @Override
    public void setUp() {
        /*SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private final io.netty.buffer.ByteBuf"),
                        new FieldFilter("private static boolean"),
                },
                -1,
                "net.minecraft"
        );*/
        Class<?>[] superByteBufClasses = Arrays.stream(Myth.classes).filter(aClass -> aClass.getSuperclass() != null).filter(aClass -> aClass.getSuperclass().toString().contains("ByteBuf")).toArray(Class<?>[]::new);
        //Class<?> clazz = classMatcher.matchFrom(superByteBufClasses)[0];
        setClazz(superByteBufClasses[0]);
    }

    @Override
    public void generateFields() {

    }
}

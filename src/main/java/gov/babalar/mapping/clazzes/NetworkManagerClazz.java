package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;

public class NetworkManagerClazz extends MappingClazz {


    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("public static final org.apache.logging.log4j.Marker"),
                        new FieldFilter("public static final org.apache.logging.log4j.Marker"),
                        new FieldFilter("public static final io.netty.util.AttributeKey"),
                        new FieldFilter("private final java.util.Queue"),
                        new FieldFilter("private final java.util.concurrent.locks.ReentrantReadWriteLock"),
                        new FieldFilter("private io.netty.channel.Channel"),
                        new FieldFilter("private boolean"),
                        new FieldFilter("private boolean"),
                },
                -1,
                "net.minecraft"
        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {

    }
}

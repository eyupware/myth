package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.util.Arrays;

/**
 * ----------
 * 9/4/2023
 * 8:32 PM
 * ----------
 **/
public class EntityPlayerClazz extends MappingClazz {
    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private net.minecraft.BlockPos"),
                        new FieldFilter("public net.minecraft.BlockPos"),
                        new FieldFilter("private net.minecraft.BlockPos"),
                        new FieldFilter("private final com.mojang.authlib.GameProfile"),
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

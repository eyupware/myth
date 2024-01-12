package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.util.Arrays;

public class PlayerControllerMPClazz extends MappingClazz {


    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private float"),
                        new FieldFilter("private net.minecraft.BlockPos"),
                        new FieldFilter("private boolean"),
                        new FieldFilter("private long"),
                        new FieldFilter("private int"),
                        new FieldFilter("private int"),
                        new FieldFilter("private float"),
                },
                -1,
                "net.minecraft.client"

        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {
        this.getFields().put("playerController", Arrays.stream(ReflectionUtils.getAllFields(MainMapping.map.get("Minecraft").getClazz())).filter(field -> field.getType().equals(this.getClazz())).findFirst().orElse(null));

    }
}

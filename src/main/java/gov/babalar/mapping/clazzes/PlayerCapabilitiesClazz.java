package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;
import gov.babalar.utils.ReflectionUtils;

import java.util.Arrays;

public class PlayerCapabilitiesClazz extends MappingClazz {

    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("private float"),
                        new FieldFilter("private float"),
                        new FieldFilter("public boolean"),
                        new FieldFilter("public boolean"),
                        new FieldFilter("public boolean"),
                },
                -1,
                "net.minecraft"
        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {
        this.getFields().put("capabilities", Arrays.stream(ReflectionUtils.getAllFields(MainMapping.map.get("EntityPlayer").getClazz())).filter(field -> field.toString().contains(this.getClazz().getName())).findFirst().orElse(null));
    }
}

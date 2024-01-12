package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.utils.ReflectionUtils;

import java.util.Arrays;

/**
 * ----------
 * 9/7/2023
 * 3:06 AM
 * ----------
 **/
public class BlockPosClazz extends MappingClazz {

    @Override
    public void setUp() {
        Class<?> blockPosClass = Arrays.stream(Myth.classes).filter(aClass -> aClass.getName().contains("BlockPos")).findFirst().orElse(null);
        setClazz(blockPosClass);
    }

    @Override
    public void generateFields() {
        this.getMethods().put("offset", Arrays.stream(ReflectionUtils.getAllMethods(this.getClazz()))
                .filter(method -> method.getReturnType().equals(this.getClazz()))
                .filter(method -> method.getParameterCount() == 1)
                .filter(method -> method.getParameterTypes()[0].equals(MainMapping.map.get("EnumFacing").getClazz()))
                .findFirst().orElse(null));
    }
}

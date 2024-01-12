package gov.babalar.mapping.clazzes;

import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;

/**
 * ----------
 * 9/4/2023
 * 8:32 PM
 * ----------
 **/
public class EntityPlayerSPClazz extends MappingClazz {

    @Override
    public void setUp() {
        SimpleClassMatcher classMatcher = new SimpleClassMatcher(
                new MethodFilter[]{},
                new FieldFilter[]{
                        new FieldFilter("public float"),
                        new FieldFilter("private java.lang.String"),
                        new FieldFilter("private boolean"),
                        new FieldFilter("protected int"),
                        new FieldFilter("public float"),
                        new FieldFilter("private double"),
                        new FieldFilter("private double"),
                        new FieldFilter("public float"),
                        new FieldFilter("private int"),
                        new FieldFilter("private short"),
                        new FieldFilter("private float"),
                        new FieldFilter("private boolean"),
                        new FieldFilter("public int"),
                        new FieldFilter("private double"),
                        new FieldFilter("private int"),
                        new FieldFilter("private float"),
                        new FieldFilter("private float"),
                        new FieldFilter("private boolean"),
                        new FieldFilter("public float"),
                        new FieldFilter("public float")
                },
                -1,
                "net.minecraft.client"
        );
        Class<?> clazz = classMatcher.matchFrom(Myth.classes)[0];
        setClazz(clazz);
    }

    @Override
    public void generateFields() {

    }
}

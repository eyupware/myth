package gov.babalar.mapping.clazzes;

import gov.babalar.Main;
import gov.babalar.Myth;
import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.matchers.FieldFilter;
import gov.babalar.matchers.MethodFilter;
import gov.babalar.matchers.SimpleClassMatcher;

public class ItemStackClazz extends MappingClazz {


    @Override
    public void setUp() {
       SimpleClassMatcher classMatcher = new SimpleClassMatcher(
               new MethodFilter[]{},
               new FieldFilter[]{
                       new FieldFilter("public static final java.text.DecimalFormat"),
                       new FieldFilter("public int"),
                       new FieldFilter("public int"),
                       new FieldFilter("private int"),
                       new FieldFilter("private boolean"),
                       new FieldFilter("private boolean")
                },
                -1,
                "net.minecraft"
        );
        Class<?>[] clazz = classMatcher.matchFrom(Myth.classes);
        setClazz(clazz[0]);
    }

    @Override
    public void generateFields() {

    }
}

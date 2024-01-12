package gov.babalar.mapping.clazzes;

import gov.babalar.mapping.components.MappingClazz;

/**
 * ----------
 * 9/4/2023
 * 8:37 PM
 * ----------
 **/
public class AutoClazzGeneration extends MappingClazz {
    public Class<?> toInsert;

    public AutoClazzGeneration(Class<?> toInsert) {
        this.toInsert = toInsert;
        setUp();
    }

    @Override
    public void setUp() {
        setClazz(toInsert);
    }

    @Override
    public void generateFields() {}
}

package gov.babalar.matchers;

import gov.babalar.Main;

import java.lang.reflect.Field;

/**
 * ----------
 * 9/1/2023
 * 12:26 AM
 * ----------
 **/
public class FieldFilter {
    private String key;


    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public FieldFilter(String key) {
        this.key = key;
    }
    public boolean isMatchWith(Field field)
    {
        String[] value = field.toString().split(" ");
        for (String string : key.split(" ")) {
            boolean matched = false;
            for (String s : value) {
                if(!s.contains(string))
                    continue;
                matched = true;
                break;
            }
            if(!matched)
                return false;
        }
        return true;
    }
}

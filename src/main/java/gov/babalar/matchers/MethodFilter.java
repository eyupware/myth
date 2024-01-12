package gov.babalar.matchers;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ----------
 * 9/1/2023
 * 12:26 AM
 * ----------
 **/
public class MethodFilter {
    private String key;
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public MethodFilter(String key) {
        this.key = key;
    }
        public boolean isMatchWith(Method method)
        {
            String[] value = method.toString().split(" ");
            for (int i = 0; i < value.length; i++) {
                String string = value[i];
                Pattern pattern = Pattern.compile("\\((.*?)\\)");
                Matcher matcher = pattern.matcher(string);
                if (matcher.find()) {
                    value[i] = String.format("(%s)" , matcher.group(1));
                    break;
                }
            }
            for (String string : key.split(" ")) {
                boolean matched = false;
                for (String s : value) {
                    if(!s.equalsIgnoreCase(string))
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

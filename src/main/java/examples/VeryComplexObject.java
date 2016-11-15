package examples;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by atrposki on 11/15/2016.
 */
public class VeryComplexObject {
    public String onlyValueNoNamePrinted = "onlyValueNoNameProperty";

    public Set<Integer> collectionOfIntegers(){
        HashSet<Integer> integers = new HashSet<>();
        Arrays.asList(1,2,3,4,5).forEach(x->integers.add(x));
        return  integers;
    }
    public List<String> collectionOfStrings(){
        return Arrays.asList("str1","str2","str3","str4","str5");
    }
    public String property="property";
    public String computedProperty(){
        return "computedProperty";
    }
}

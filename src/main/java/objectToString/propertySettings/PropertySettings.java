package objectToString.propertySettings;

import objectToString.PrintSettings;

import java.util.function.Function;

/**
 * Created by atrposki on 11/15/2016.
 */
public class PropertySettings<T> extends BasePropertySettings<T> {
    public PropertySettings(PrintSettings<T> parent, Function<T, String> valueMaper){
        super(parent,valueMaper);
        parent.Add(this);
    }
}

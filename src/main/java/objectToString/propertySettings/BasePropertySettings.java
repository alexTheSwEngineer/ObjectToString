package objectToString.propertySettings;

import objectToString.PrintSettings;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by atrposki on 11/15/2016.
 */
public abstract class BasePropertySettings<T>
{
    private PrintSettings<T> parent;
    private Function<T,String> value;
    private Function<T,String> name;


    protected BasePropertySettings(PrintSettings<T> parent, Function<T,String> value)
    {
        this.parent=parent;
        setValue(value);
        setName(x->"");
    }

    protected BasePropertySettings(PrintSettings<T> parent)
    {
        this.parent=parent;
        setValue(x->"");
        setName(x->"");
    }

    protected void setValue(Function<T, String> value) {
        this.value = value;
    }

    protected void setName(Function<T, String> name) {
        this.name = name;
    }

    public BasePropertySettings<T> column(Function<T,String> valueMaper){
        return new PropertySettings<T>(this.parent,valueMaper);
    }

    public <Q> BasePropertySettings<T> column(String delimiter,Function<T,Collection<Q>> valueMaper){
        return new PropertySettings<T>(this.parent, (obj)->{
            List<String> collection=valueMaper.apply(obj)
                                     .stream()
                                     .map(x->x.toString())
                                     .collect(Collectors.toList());
            return String.join(delimiter,collection);
        });
    }

    public BasePropertySettings<T> name(String name){
        this.name = x->name;
        return this;
    }

    public BasePropertySettings<T> name(Function<T,String> nameGenerator){
        name =nameGenerator;
        return this;
    }

    public PrintSettings<T> Build()
    {
        return  new PrintSettings<T>(this.parent);
    }

    public Property<T> apply(T obj){
        return  new Property<T>(name.apply(obj), value.apply(obj));
    }
}

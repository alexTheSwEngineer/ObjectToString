package objectToString;


import main.batchJob.interfaces.IBatchAction;
import main.common.BatchException;
import main.common.FunctionWithException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by atrposki on 11/15/2016.
 */
public class PropertyPrintSetting<TSource> {
    PropertyPrintSetting<TSource> previous=null;
    public Function<TSource,String> name=PropertyPrintSetting::empty;
    public Function<TSource,String> value=PropertyPrintSetting::empty;

    public PropertyPrintSetting(Function<TSource,String> value) {
        this.value=value;
    }

    public <Q> PropertyPrintSetting(String delimiter,Function<TSource,Collection<Q>> val) {
        this.value=fromCollection(delimiter,val);
    }


    public PropertyPrintSetting<TSource> property(Function<TSource,String> value){
        PropertyPrintSetting<TSource> newSetting = new PropertyPrintSetting<TSource>(value);
        newSetting.previous=this;
        return  newSetting;
    }



    public <Q> PropertyPrintSetting<TSource> property(String delimiter, Function<TSource,Collection<Q>> val){
        return  this.property( fromCollection(delimiter,val));
    }

    public  PropertyPrintSetting<TSource> name(Function<TSource,String> name){
        this.name=name;
        return this;
    }
    public PropertyPrintSetting<TSource> name(String nameStr){
        return name(x->nameStr);
    }

    public Iterable<IBatchAction<TSource,Property>> buildObjectPrintJob() throws BatchException {
        List<IBatchAction<TSource,Property>> res = new LinkedList<>();
        PropertyPrintSetting<TSource> current = this;
        while (current!=null){
            res.add(current.createAction());
            current=current.previous;
        }
        Collections.reverse(res);
        return res;
    }

    private Function<TSource,Property> mapToProperty(){
        return  x->new Property(name.apply(x),value.apply(x));
    }

    private  static <q,TSource> Function<TSource,String> fromCollection(String delimiter,Function<TSource,Collection<q>> value)
    {
        return obj->String.join(delimiter,value.apply(obj)
                .stream()
                .map(x->x.toString())
                .collect(Collectors.toList()));
    }

    private static <TSource> String empty(TSource source){return "";}
    private IBatchAction<TSource,Property> createAction(){
        return  new IBatchAction<TSource, Property>() {
            @Override
            public boolean allow(TSource input) {
                return true;
            }

            @Override
            public boolean breakBefore(TSource input) {
                return false;
            }

            @Override
            public boolean breakAfter(TSource input, Property output) {
                return false;
            }

            @Override
            public Property accept(TSource in) throws BatchException {
                return new Property(name.apply(in),value.apply(in));
            }
        };
    }

}

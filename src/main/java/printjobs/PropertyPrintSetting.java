package printjobs;

import batchJob.BatchJob;
import batchJob.BatchSettings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by atrposki on 11/15/2016.
 */
public class PropertyPrintSetting<TSource> {
    PropertyPrintSetting<TSource> previous=null;
    private Function<TSource,String> name=PropertyPrintSetting::empty;
    private Function<TSource,String> value=PropertyPrintSetting::empty;

    public PropertyPrintSetting(Function<TSource,String> value) {
        this.value=value;
    }

    public <Q> PropertyPrintSetting(String delimiter,Function<TSource,Collection<Q>> val) {
        this.value=fromCollection(delimiter,val);
    }


    public PropertyPrintSetting<TSource> create(Function<TSource,String> value){
        PropertyPrintSetting<TSource> newSetting = new PropertyPrintSetting<TSource>(value);
        newSetting.previous=this;
        return  newSetting;
    }

    public <Q> PropertyPrintSetting<TSource> create(String delimiter,Function<TSource,Collection<Q>> val){
        return  this.create( fromCollection(delimiter,val));
    }

    public  PropertyPrintSetting<TSource> name(Function<TSource,String> name){
        this.name=name;
        return this;
    }
    public PropertyPrintSetting<TSource> name(String nameStr){
        return name(x->nameStr);
    }

    public   BatchSettings<TSource,Property> buildSettings(){
        List<PropertyPrintSetting<TSource>> res = new ArrayList<>();
        PropertyPrintSetting<TSource> current = this;
        while (current.hasPrevious()){
            res.add(this);
        }
        Collections.reverse(res);
        return new BatchSettings<TSource,Property>(res.stream()
           .map(x->mapToProperty())
           .collect(Collectors.toList()));
    }

    public   <TParam> BatchJob<TSource,Property,TParam> buildJob(){
        return buildSettings().<TParam>createJob();
    }

    private boolean hasPrevious() {
        return previous!=null;
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
    private static  <Src> String empty(Src source){
        return "";
    }

}

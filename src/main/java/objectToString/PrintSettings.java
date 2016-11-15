package objectToString;

import objectToString.propertySettings.BasePropertySettings;
import objectToString.propertySettings.InitialSettingsNode;
import objectToString.propertySettings.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by atrposki on 11/14/2016.
 */
public class PrintSettings<T> {
    private List<BasePropertySettings<T>> propertyPrinters;
    private Stream<Property<T>> propertyStream(T obj){
        return  propertyPrinters.stream().map(x->x.apply(obj));
    }

     public PrintSettings(){
        this.propertyPrinters=new ArrayList<>();
    }

     public PrintSettings(PrintSettings<T> other) {
        this.propertyPrinters=other.propertyPrinters
                                   .stream()
                                   .collect(Collectors.toList());
    }

    public void printValues(T paper,Consumer<String>  ps) throws Exception {
        this.createPrintJob().printEach(x->ps.accept(x.getValue()));
    }

    public void printHeaders(T paper,Consumer<String>  ps)throws Exception {
        this.createPrintJob().printEach(x->ps.accept(x.getName()));
    }

    public void printProperties(T paper,  BiConsumer<String,String> ps)throws Exception{
        this.createPrintJob().printEach(x->ps.accept(x.getName(),x.getValue()));
    }

    public BasePropertySettings<T> SetUp()
    {
        return new InitialSettingsNode<T>(this);
    }
    public void Add(BasePropertySettings<T> propertySetUp){
        propertyPrinters.add(propertySetUp);
    }

    public void print(T obj, Consumer<Property<T>> before, Consumer<Property<T>> print, BiConsumer<Property<T>, Property<T>> between, Consumer<Property<T>> after) throws Exception {
        List<Property<T>> properties = propertyStream(obj)
                                       .collect(Collectors.toList());

        if(!properties.isEmpty()){
            Property<T> first = properties.get(0);
            before.accept(first);
        }

        Property<T> previous=null;
        for (Property<T> current : properties) {
            if(previous!=null){
                between.accept(previous,current);
            }
            print.accept(current);
            previous=current;
        }

        if(!propertyPrinters.isEmpty()){
            after.accept(previous);
        }
    }

    public PrintJob<T> createPrintJob(){
        return  new PrintJob<T>(this);
    }


}

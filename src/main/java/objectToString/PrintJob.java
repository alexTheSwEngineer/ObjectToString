package objectToString;

import objectToString.propertySettings.Property;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by atrposki on 11/15/2016.
 */
public class PrintJob<T> {
    private PrintSettings<T> settings;
    public PrintJob(PrintSettings<T> settings){
        this.settings=settings;
        before= PrintJob::NOP;
        print= PrintJob::NOP;
        between= PrintJob::NOP;
        after= PrintJob::NOP;
    }

    public PrintJob(PrintJob<T> other){
        this.settings=other.settings;
        this.before=other.before;
        this.print=other.print;
        this.between=other.between;
        this.after=other.after;
    }

    private Consumer<Property<T>> before;
    private Consumer<Property<T>> print;
    private BiConsumer<Property<T>, Property<T>> between;
    private Consumer<Property<T>> after;


    public PrintJob<T> beforeAll(Consumer<Property<T>> before) {
        PrintJob<T> res = new PrintJob<>(this);
        res.before=PrintJob.DefaultIfNull(before);
        return  res;
    }

    public PrintJob<T> printEach(Consumer<Property<T>> print) {
        PrintJob<T> res = new PrintJob<>(this);
        res.print=PrintJob.DefaultIfNull(print);
        return  res;
    }

    public PrintJob<T> between(BiConsumer<Property<T>, Property<T>> between) {
        if(between==null){
            this.between= PrintJob::NOP;
        }else {
            this.between = between;
        }

        return this;
    }

    public PrintJob<T> afterAll(Consumer<Property<T>> after) {
        PrintJob<T> res = new PrintJob<>(this);
        res.after=PrintJob.DefaultIfNull(after);
        return  res;
    }

    public PrintJob<T> print(T obj) throws Exception {
        settings.print(obj,before,print,between,after);
        return this;
    }

    private static <T> void NOP(Property<T> property){}

    private static <T> void NOP(Property<T> l, Property<T> r){}

    private  static <T> Consumer<Property<T>> DefaultIfNull(Consumer<Property<T>> consumer){
        if(consumer!=null){
            return consumer;
        }
        return PrintJob::NOP;
    }
}

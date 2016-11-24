package examples;

import main.batchJob.BatchJobExecution;
import main.batchJob.JobEnhancements;
import main.batchJob.SimpleBatchAction;
import main.batchJob.interfaces.IBatchAction;
import main.batchJob.interfaces.IBatchActionExecution;
import main.common.BatchException;
import main.itteration.Chain;
import main.processing.IBreakConsumer;
import objectToString.Property;
import objectToString.PropertyPrintSetting;

import java.util.ArrayList;

/**
 * Created by atrposki on 11/15/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {

//        Iterable<IBatchAction<VeryComplexObject, Property>> printVeryComplexObject =
//        new PropertyPrintSetting<VeryComplexObject>(x -> x.computedProperty())
//        .name(x->"p1111")
//        .property(x->x.computedProperty())
//        .name(x->"p2222")
//        .property(x->x.computedProperty())
//        .name(x->"p3333")
//        .buildObjectPrintJob();
//
//        final IBreakConsumer<Chain<IBatchActionExecution<VeryComplexObject, Property, IBatchAction<VeryComplexObject, Property>>, BatchException>> settings =
//        new JobEnhancements<VeryComplexObject, Property, IBatchAction<VeryComplexObject, Property>>()
//        .beforeFirst(x ->p(" beforeFirst "))
//        .beforeLast(x->p(" beforeLast "))
//        .beforeEach(x->pl(" beforeEach "))
//        .between((l, r) -> pl("between\n"))
//        .afterEach(x ->pl(x.getResult().toString()+" afterEeach"))
//        .afterFirst(x -> p(" afterFirst"))
//        .afterLast(x -> p(" afterLast"))
//        .onException(x->pl("\n\n\n EXCEPTIOON"))
//        .build();
//
//        new BatchJobExecution<VeryComplexObject,Property,IBatchAction<VeryComplexObject,Property>>(settings,printVeryComplexObject)
//        .execute(new VeryComplexObject());


        ArrayList<SimpleBatchAction<Integer,String>> lst = new ArrayList<>();
        lst.add(SimpleBatchAction.create(x-> x.toString()));
        lst.add(SimpleBatchAction.createWithEx(x->{
            throw new NullPointerException();
        }));
        lst.add(SimpleBatchAction.create(x-> x.toString()));

//        pl("");
//        pl("");
//        pl("");
//        pl("");

        IBreakConsumer<Chain<IBatchActionExecution<Integer, String, SimpleBatchAction<Integer, String>>, BatchException>> exceptioooooooon = new JobEnhancements<Integer, String, SimpleBatchAction<Integer, String>>()
        .onException(x -> {System.out.println("EXCEPTIOOOOOOOON"); })
        .afterEach(x -> System.out.println(x.getResult()))
        .build();

        BatchJobExecution.<Integer,String,SimpleBatchAction<Integer,String>>Execute(lst.iterator(),123,exceptioooooooon);

    }

    private static void  pl(String str){
        System.out.println(str);
    }
    private static  void  p(String str){
        System.out.print(str);
    }
}


package examples;

import main.batchJob.JobEnhancements;
import main.batchJob.interfaces.IBatchAction;
import main.batchJob.interfaces.IBatchActionExecution;
import main.common.BatchException;
import main.itteration.Chain;
import main.processing.IBreakConsumer;
import objectToString.Property;
import objectToString.PropertyPrintSetting;

/**
 * Created by atrposki on 11/15/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        Iterable<IBatchAction<VeryComplexObject, Property>> minimalPrintSettings =
        new PropertyPrintSetting<VeryComplexObject>(x -> x.property)
        .name("NameOfProperty")
        .property(x -> x.computedProperty())
        .name(x -> "computed name of object" + x.toString())
        .property(x -> x.onlyValueNoNamePrinted)
        .name("")
        .buildObjectPrintJob();


        JobEnhancements<VeryComplexObject, Property, IBatchAction<VeryComplexObject, Property>> printToCOnsole = new JobEnhancements<VeryComplexObject, Property, IBatchAction<VeryComplexObject, Property>>()
        .beforeFirst(x -> System.out.println("AWESOME HEADER"))
        .between((l, r) -> System.out.print(","))
        .afterEach(x -> System.out.print(x.getResult().getValue()))
        .onException(x -> x.breakJob());


        printToCOnsole.execute(minimalPrintSettings,new VeryComplexObject());


    }


    private static void  pl(String str){
        System.out.println(str);
    }
    private static  void  p(String str){
        System.out.print(str);
    }
}


package examples;

import main.batchJob.SimpleBatchAction;
import objectToString.ExtractPropertyAction;
import objectToString.ObjectPrintSettings;
import objectToString.PropertyPrintSetting;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by atrposki on 11/15/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        //OBJECT PRINTING
        Iterable<ExtractPropertyAction<VeryComplexObject>> propertySettings =
        new PropertyPrintSetting<VeryComplexObject>(x -> x.property)
        .name("HardCoddedNameOfProperty")
        .property(x -> x.computedProperty())
        .name(x -> "computed name of object" + x.toString())
        .property(x -> x.onlyValueNoNamePrinted)
        .name("")
        .buildObjectPrintJob();

        ObjectPrintSettings<VeryComplexObject> objectPrintSettings = new ObjectPrintSettings<VeryComplexObject>()
        .beforeFirst(x -> System.out.println("AWESOME HEADER"))
        .between((l, r) -> System.out.print(","))
        .afterEach(x -> System.out.print(x.getResult().getValue()))
        .onException(x -> x.breakJob());

        VeryComplexObject objectToPrint = new VeryComplexObject();
        objectPrintSettings.execute(propertySettings.iterator(),objectToPrint);

        //Complex actions of large stream that cannot possibly be in memmory at once:

        IntegerToActionTransformer isDivisableActions = createManyActions();

        Counter devisableByInput = new Counter();
        new IntegerToBooleanJobSettings()
            .beforeEach(x-> System.out.print(","))
            .afterEach(x->{
                if(x.getResult()){
                    devisableByInput.increment();
                }
            })
            .execute(isDivisableActions,13);

        System.out.println();
        System.out.println(devisableByInput.getCount());


    }

    private static IntegerToActionTransformer createManyActions(){
        ArrayList<Integer> largeArray = new ArrayList<Integer>();
        for (int i = 1; i < 1200000; i++) {
            largeArray.add(i);
        }

        Counter divisableByInput=new Counter();
       return new IntegerToActionTransformer(largeArray.iterator(),
                                        valueToTransform->{
                                            return new SimpleBatchAction<Integer,Boolean>()
                                                    .act(divisor->valueToTransform%divisor==0);
                                        });
    }

}


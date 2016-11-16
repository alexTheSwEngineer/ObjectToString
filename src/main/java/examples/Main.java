package examples;

import batchJob.BatchJob;
import batchJob.BatchSettings;
import objectToString.Property;
import objectToString.PropertyPrintSetting;

import java.io.PrintStream;

/**
 * Created by atrposki on 11/15/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        BatchSettings<VeryComplexObject, Property> settings = new PropertyPrintSetting<VeryComplexObject>(x -> x.computedProperty())
                .name(x -> x.computedProperty() + "name is computed property")
                .create(x -> x.onlyValueNoNamePrinted)
                .create(x -> x.property)
                .name("Just property")
                .create(", ", x -> x.collectionOfStrings())
                .buildSettings();
        BatchJob<VeryComplexObject, Property, PrintStream> printJob = settings.<PrintStream>createJob()
                .beforeFirst((prop, stream) -> stream.println("++++++++++\n"))
                .beforeEach((prop, stream) -> stream.print("|"))
                .applyOnEach((prop, stream) -> stream.print(prop.name + ":" + prop.value))
                .afterEach((prop, stream) -> stream.print("!\n"))
                .betweenEach((l, r, stream) -> stream.println())
                .afterLast((prop, stream) -> stream.println("\n++++++++++\n"));


        StringBuilder sb = new StringBuilder();
        printJob.execute(new VeryComplexObject(),System.out);
        printJob.execute(new VeryComplexObject(),new PrintStream("C:\\Users\\atrposki\\file.txt"));
        System.out.println(sb.toString());
    }
}

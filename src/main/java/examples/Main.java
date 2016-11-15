package examples;

import batchJob.BatchJob;
import printjobs.Property;
import printjobs.PropertyPrintSetting;

import java.io.PrintStream;

/**
 * Created by atrposki on 11/15/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        BatchJob<VeryComplexObject, Property, PrintStream> printJob = new PropertyPrintSetting<VeryComplexObject>(x -> x.computedProperty())
        .name(x -> x.computedProperty() + "name is computed property")
        .create(x -> x.onlyValueNoNamePrinted)
        .create(x -> x.property)
        .name("Just property")
        .create(", ", x -> x.collectionOfStrings())
        .<PrintStream>buildJob()
        .beforeAll((prop, stream) -> stream.println("++++++++++++++++"))
        .afterAll((prop, stream) -> stream.println("++++++++++++++++"))
        .applyOnEach((prop, stream) -> stream.print(prop.name + "" + prop.value))
        .between((l, r, stream) -> stream.print(";   "));

        printJob.execute(new VeryComplexObject(),System.out);
    }
}

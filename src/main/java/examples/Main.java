package examples;

import batchJob.BatchJob;
import batchJob.BatchSettings;
import printjobs.Property;
import printjobs.PropertyPrintSetting;

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
        BatchJob<VeryComplexObject, Property, StringBuilder> printJob = settings.<StringBuilder>createJob()
                .first((prop, stream) -> stream.append("++++++++++\n"))
                .last((prop, stream) -> stream.append("++++++++++++++++\n"))
                .applyOnEach((prop, stream) -> stream.append(prop.name + "" + prop.value))
                .betweenEach((l, r, stream) -> stream.append(";   "));

        System.out.println("++++++++++++++++++++++++++++++");
        StringBuilder sb = new StringBuilder();
        printJob.execute(new VeryComplexObject(),sb);
        System.out.println(sb.toString());
    }
}

package examples;

import com.sun.org.apache.xpath.internal.SourceTree;
import objectToString.PrintSettings;

/**
 * Created by atrposki on 11/15/2016.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        VeryComplexObject obj = new VeryComplexObject();
        PrintSettings<VeryComplexObject> onePropertyOnly = new PrintSettings<VeryComplexObject>()
        .SetUp()
        .column(x->x.property)
        .name(x->"this property is named property")
        .Build();
        PrintSettings<VeryComplexObject> allTheREst = onePropertyOnly.SetUp()
        .column(x->x.computedProperty())
        .name(x->"computed property name for object with hash code:"+x.hashCode())
        .column(x->x.onlyValueNoNamePrinted)
        .column(" , ",x->x.collectionOfIntegers())
        .name("Set of integers delimited by a comma")
        .column("; ",x->x.collectionOfStrings())
        .name("strings separated by \";\"")
        .Build();

        onePropertyOnly.printValues(obj,x->System.out.println(x));
        onePropertyOnly.printHeaders(obj,x->System.out.println(x));

        allTheREst.createPrintJob()
                .beforeAll(firstProp->{
                        System.out.println("+++++++++++++++++++"+firstProp.getName()+"+++++++++++++++++++++");
                })
                .between((l,r)-> System.out.println("\n"))
                .afterAll(x-> System.out.println("++++++++++++++++++++++++++++++++++++++++") )
                .printEach(x-> System.out.println(x.getName()+":"+x.getValue()))
                .print(obj);


    }
}

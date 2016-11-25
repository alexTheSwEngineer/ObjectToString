package processTests.streamProcess;
import main.common.BatchException;
import main.processing.ActionProcess;
import main.processing.IBreakConsumer;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Created by atrposki on 25-Nov-16.
 */
@RunWith(Theories.class)
public class FunctionalProcess_BreaksCorrectly {

    public static final String TESTCASES = "testCases";
    public static final String PROPAGATE_BREAkS = "truefalse";
    @Theory
    public void run(@FromDataPoints(TESTCASES) StreamProcessTestCase testCase, @FromDataPoints(PROPAGATE_BREAkS) boolean propagateBreaks) throws BatchException {
        ActionProcess<StringBuilder> sut = testCase.buildSut(propagateBreaks);
        StringBuilder actuall = new StringBuilder();

        sut.accept(actuall);

        Assert.assertEquals(testCase.toString(),testCase.getExpected(),actuall.toString());

    }

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d1 = new StreamProcessTestCase()
            .descIs("FirstAction_BreaksBefore")
            .addAction(create(true,true,false,1))
            .addAction(create(true,false,false,2))
            .addAction(create(true,false,false,3))
            .expects("");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d2 = new StreamProcessTestCase()
            .descIs("FirstAction_BreaksAfter")
            .addAction(create(true,false,true,1))
            .addAction(create(true,false,false,2))
            .addAction(create(true,false,false,3))
            .expects("1");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d3 = new StreamProcessTestCase()
            .descIs("FirstAction_Skips")
            .addAction(create(false,false,false,1))
            .addAction(create(true,false,false,2))
            .addAction(create(true,false,false,3))
            .expects("23");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d5 = new StreamProcessTestCase()
            .descIs("FirstAction_SkipsAndBreaksAfter")
            .addAction(create(false,false,true,1))
            .addAction(create(true,false,false,2))
            .addAction(create(true,false,false,3))
            .expects("");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d6 = new StreamProcessTestCase()
            .descIs("FirstAction_SkipsAndBreaksBefore")
            .addAction(create(false,true,false,1))
            .addAction(create(true,false,false,2))
            .addAction(create(true,false,false,3))
            .expects("");
    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d7 = new StreamProcessTestCase()
            .descIs("FirstAction_SkipsAndBreaksBeforeAndBreaksAfter")
            .addAction(create(false,true,true,1))
            .addAction(create(true,false,false,2))
            .addAction(create(true,false,false,3))
            .expects("");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d8 = new StreamProcessTestCase()
            .descIs("MiddleAction_Skips")
            .addAction(create(true,false,false,1))
            .addAction(create(false,false,false,2))
            .addAction(create(true,false,false,3))
            .expects("13");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d9 = new StreamProcessTestCase()
            .descIs("MiddleAction_BreaksBefore")
            .addAction(create(true,false,false,1))
            .addAction(create(false,true,false,2))
            .addAction(create(true,false,false,3))
            .expects("1");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d10 = new StreamProcessTestCase()
            .descIs("MiddleAction_BreaksBefore_AndSkips")
            .addAction(create(true,false,false,1))
            .addAction(create(false,true,false,2))
            .addAction(create(true,false,false,3))
            .expects("1");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d11 = new StreamProcessTestCase()
            .descIs("MiddleAction_BreaksAfter_AndSkips")
            .addAction(create(true,false,false,1))
            .addAction(create(false,false,true,2))
            .addAction(create(true,false,false,3))
            .expects("1");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d12 = new StreamProcessTestCase()
            .descIs("MiddleAction_BreaksAfter")
            .addAction(create(true,false,false,1))
            .addAction(create(true,false,true,2))
            .addAction(create(true,false,false,3))
            .expects("12");

    @DataPoint(TESTCASES)
    public static StreamProcessTestCase d13 = new StreamProcessTestCase()
            .descIs("MiddleAction_BreaksAfter_AndSkips_andBreaksBefore")
            .addAction(create(true,false,false,1))
            .addAction(create(false,false,true,2))
            .addAction(create(true,false,false,3))
            .expects("1");

    public static IBreakConsumer<StringBuilder> create(boolean allows,boolean breakBefore,boolean breakAfter,int name) {
        return new IBreakConsumerBuilder<StringBuilder>()
                .allows(allows)
                .breaksBefore(breakBefore)
                .breaksAfter(breakAfter)
                .act(x->x.append(name))
                .build();
    }
}

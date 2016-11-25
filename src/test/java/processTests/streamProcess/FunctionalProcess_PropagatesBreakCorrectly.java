package processTests.streamProcess;

import main.common.BatchException;
import main.processing.ActionProcess;
import main.processing.IBreakConsumer;
import org.junit.Assert;
import org.junit.experimental.theories.*;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * Created by atrposki on 25-Nov-16.
 */
@RunWith(Theories.class)
public class FunctionalProcess_PropagatesBreakCorrectly {

    public static final String BREAK_PROPAGATES= "BREAK_PROPAGATES";
    @DataPoint(BREAK_PROPAGATES)
    public static List<IBreakConsumer> dp1=
        Arrays.asList(new IBreakConsumerBuilder().allows(true).breaksBefore(true).build());

    @DataPoint(BREAK_PROPAGATES)
    public static List<IBreakConsumer> dp2=
        Arrays.asList(new IBreakConsumerBuilder().allows(true).breaksAfter(true).build());
    @DataPoint(BREAK_PROPAGATES)
    public static List<IBreakConsumer> dp3=
        Arrays.asList(new IBreakConsumerBuilder().allows(true).build(),
                      new IBreakConsumerBuilder().allows(true).breaksAfter(true).build());
    @DataPoint(BREAK_PROPAGATES)
    public static List<IBreakConsumer> dp4=
        Arrays.asList(new IBreakConsumerBuilder().allows(true).build(),
                      new IBreakConsumerBuilder().allows(true).breaksBefore(true).build());
    @DataPoint(BREAK_PROPAGATES)
    public static List<IBreakConsumer> dp5=
        Arrays.asList(new IBreakConsumerBuilder().allows(false).build(),
                      new IBreakConsumerBuilder().allows(true).breaksBefore(true).build(),
                      new IBreakConsumerBuilder().allows(true).build());
    @DataPoint(BREAK_PROPAGATES)
    public static List<IBreakConsumer> dp6=
        Arrays.asList(new IBreakConsumerBuilder().allows(false).build(),
                new IBreakConsumerBuilder().allows(true).breaksAfter(true).build(),
                new IBreakConsumerBuilder().allows(true).build());

    @DataPoint(BREAK_PROPAGATES)
    public static List<IBreakConsumer> dp7=
        Arrays.asList(new IBreakConsumerBuilder().allows(true).build(),
                new IBreakConsumerBuilder().allows(true).build(),
                new IBreakConsumerBuilder().allows(true).breaksBefore(true).build());



    @Theory
    public void FunctionProcess_PropagatesBreak(@FromDataPoints(BREAK_PROPAGATES)List<IBreakConsumer<Integer>> actions) throws BatchException {
        Integer someInput = 1;
        ActionProcess<Integer> sut = new ActionProcess<>(true);
        for (IBreakConsumer<Integer> action :
                actions) {
            sut.add(action);
        }

        sut.accept(someInput);

        Assert.assertFalse(sut.breakBeforeExec());
        Assert.assertTrue(sut.breakAfterExecc());
    }

    @Theory
    public void FunctionProcess_DoesntPropagatesBreak(@FromDataPoints(BREAK_PROPAGATES)List<IBreakConsumer<Integer>> actions) throws BatchException {
        Integer someInput = 1;
        ActionProcess<Integer> sut = new ActionProcess<>(false);
        for (IBreakConsumer<Integer> action :
                actions) {
            sut.add(action);
        }

        sut.accept(someInput);

        Assert.assertFalse(sut.breakBeforeExec());
        Assert.assertFalse(sut.breakAfterExecc());
    }

}

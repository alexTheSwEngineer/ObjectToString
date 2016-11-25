package batchActionCall;

import common.ActionBuilder;
import main.batchJob.BatchActionCall;
import main.batchJob.interfaces.IBatchAction;
import main.batchJob.interfaces.IBatchActionExecution;
import main.common.BatchException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class ActionCallTests {

    @DataPoints("TrueFalse")
    public static Boolean[] TrueFalse = {true,false};

    @Theory
    public  void BatchActionCall_propagatesSkip(@FromDataPoints("TrueFalse") boolean allowExpected ) throws BatchException {
            Integer result = 3;
            Integer input = 5;
        IBatchAction action = new ActionBuilder<Integer, Integer>()
                            .allowReturns(allowExpected)
                            .build();
        IBatchActionExecution sut = new BatchActionCall(action,input);


        boolean acutual = sut.allowExec();

        Assert.assertEquals(allowExpected,acutual);
    }

    @Theory
    public  void BatchActionCall_propagatesBreakBefore(@FromDataPoints("TrueFalse") boolean breakBeforeExoected ) throws BatchException {
        Integer result = 3;
        Integer input = 5;
        IBatchAction action = new ActionBuilder<Integer, Integer>()
                .allowReturns(true)
                .breakBeforeReturns(breakBeforeExoected)
                .build();
        IBatchActionExecution sut = new BatchActionCall(action,input);


        boolean acutual = sut.breakBeforeExec();

        Assert.assertEquals(breakBeforeExoected,acutual);
    }

    @Theory
    public  void BatchActionCall_propagatesBreakAfter(@FromDataPoints("TrueFalse") boolean breakAfterExpected ) throws BatchException {
        Integer result = 3;
        Integer input = 5;
        IBatchAction action = new ActionBuilder<Integer, Integer>()
                .allowReturns(true)
                .breakAfterReturns(breakAfterExpected)
                .build();
        IBatchActionExecution sut = new BatchActionCall(action,input);


        boolean acutual = sut.breakBeforeExec();

        Assert.assertEquals(breakAfterExpected,acutual);
    }

    @Test
    public void BatchActionCall_executesWrappedAction() throws BatchException {
        Integer expectedResult = 3;
        Integer input = 5;
        IBatchAction action = new ActionBuilder<Integer, Integer>()
                .allowReturns(true)
                .resultIs(expectedResult)
                .build();
        IBatchActionExecution sut = new BatchActionCall(action,input);

        sut.execute();

        Integer actualResult = (Integer) sut.getResult();
        Assert.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void GivenBreakBefore_WhenBatchActionCallExecutes_ItSkipsWrappedActionExecution(){
        Integer expectedResult = 3;
        Integer input = 5;
        IBatchAction action = new ActionBuilder<Integer, Integer>()
                .allowReturns(true)
                .breakBeforeReturns(true)
                .resultIs(expectedResult)
                .build();
        IBatchActionExecution sut = new BatchActionCall(action,input);

        Integer actualResult = (Integer) sut.getResult();

        Assert.assertNull(actualResult);
    }

    @Test
    public void GivenAllowsFalse_WhenBatchActionCallExecutes_ItSkipsWrappedActionExecution(){
        Integer expectedResult = 3;
        Integer input = 5;
        IBatchAction action = new ActionBuilder<Integer, Integer>()
                .allowReturns(false)
                .resultIs(expectedResult)
                .build();
        IBatchActionExecution sut = new BatchActionCall(action,input);

        Integer actualResult = (Integer) sut.getResult();

        Assert.assertNull(actualResult);
    }





}

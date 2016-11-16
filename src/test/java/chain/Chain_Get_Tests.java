package chain;

import main.itteration.Chain;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by atrposki on 24-Nov-16.
 */
@RunWith(Theories.class)
public class Chain_Get_Tests {


    private void assertChainGetIsCorrect(Chain<Integer,Exception> chain,Integer expected,String msg){
        Assert.assertEquals(msg,expected,chain.get());
    }

    @Theory
    public void ChainMove_ItteratesCorrectly(TestCase<Integer,Integer> testCase) throws Exception {
            TestCase.run(testCase,this::assertChainGetIsCorrect);
    }


    @DataPoint
    public static TestCase<Integer,Integer> t0=
    new TestCase<Integer,Integer>("GIvenEmptyIterator_get()returnsNull_inAllIterations")
    .given(new ArrayList<Integer>())
    .expectOnIteration1(null)
    .expectOnIteration2(null)
    .expectOnIteration3(null)
    .expectOnIteration4(null)
    .expectOnIteration5(null);

    @DataPoint
    public static TestCase<Integer,Integer> t1=
    new TestCase<Integer,Integer>("GIvenOneElemArray_get()returnsCorrectResults")
    .given(Arrays.asList(1))
    .expectOnIteration1(1)
    .expectOnIteration2(null)
    .expectOnIteration3(null)
    .expectOnIteration4(null)
    .expectOnIteration5(null);

    @DataPoint
    public static TestCase<Integer,Integer> t2=
    new TestCase<Integer,Integer>("GIvenTwoElemArray_get()returnsCorrectResults")
    .given(Arrays.asList(1,2))
    .expectOnIteration1(1)
    .expectOnIteration2(2)
    .expectOnIteration3(null)
    .expectOnIteration4(null)
    .expectOnIteration5(null);

    @DataPoint
    public static TestCase<Integer,Integer> t3=
    new TestCase<Integer,Integer>("GIven3ElemArray_get()returnsCorrectResults")
    .given(Arrays.asList(1,2,3))
    .expectOnIteration1(1)
    .expectOnIteration2(2)
    .expectOnIteration3(3)
    .expectOnIteration4(null)
    .expectOnIteration5(null);

    @DataPoint
    public static TestCase<Integer,Integer> t4=
    new TestCase<Integer,Integer>("GIven4ElemArray_get()returnsCorrectResults")
    .given(Arrays.asList(1,2,3,4))
    .expectOnIteration1(1)
    .expectOnIteration2(2)
    .expectOnIteration3(3)
    .expectOnIteration4(4)
    .expectOnIteration5(null);
}

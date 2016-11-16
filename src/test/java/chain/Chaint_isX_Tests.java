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
 * Created by atrposki on 22-Nov-16.
 */
@RunWith(Theories.class)
public class Chaint_isX_Tests {


    private void AssertChainState(Chain chain,ChainState state,String msg){
        Assert.assertEquals(msg+"isPresent",state.isPresent(),chain.isPresent());
        Assert.assertEquals(msg+"isFist",state.isFirst(),chain.isFirst());
        Assert.assertEquals(msg+"isBetween",state.isBetween(),chain.isBetween());
        Assert.assertEquals(msg+"isLast",state.isLast(),chain.isLast());
    }

    @Theory
    public void chainStateCorrectness(TestCase<ChainState,Integer> testCase) throws Exception {
        TestCase.run(testCase,this::AssertChainState);
    }

    @DataPoint
    public static TestCase<ChainState,Integer> t0 = new TestCase<ChainState,Integer>("givenEmptyArray_chainStateIsCorrect")
            .given(new ArrayList<Integer>())
            .expectOnIteration1(new ChainState());

    @DataPoint
    public static TestCase<ChainState,Integer> t1 =
    new TestCase<ChainState,Integer>("givenOneElemArray_chainStateIsCorrect")
    .given(Arrays.asList(1))
    .expectOnIteration1(new ChainState()
                            .isFirst(true)
                            .isLast(true)
                            .isPresent(true)
                            .isBetween(false));

    @DataPoint
    public static TestCase<ChainState,Integer> t2 =
    new TestCase<ChainState,Integer>("givenTwoElemArray_chainStateIsCorrect")
    .given(Arrays.asList(1,2))
    .expectOnIteration1(new ChainState()
                            .isFirst(true)
                            .isPresent(true)
                            .isBetween(true))
    .expectOnIteration2(new ChainState()
                            .isLast(true)
                            .isPresent(true)
                            );

    @DataPoint
    public static TestCase<ChainState,Integer> t3 =
    new TestCase<ChainState,Integer>("givenTreeElemArray_chainStateIsCorrect")
    .given(Arrays.asList(1,2,3))
    .expectOnIteration1(new ChainState()
            .isFirst(true)
            .isPresent(true)
            .isBetween(true))
    .expectOnIteration2(new ChainState()
            .isPresent(true)
            .isBetween(true))
    .expectOnIteration3(new ChainState()
            .isPresent(true)
            .isLast(true));


    @DataPoint
    public static TestCase<ChainState,Integer> t4 =
            new TestCase<ChainState,Integer>("givenOneElemArray_chainIterates4emptycycles")
                    .given(Arrays.asList(1))
                    .expectOnIteration1(new ChainState()
                            .isFirst(true)
                            .isPresent(true)
                            .isLast(true))
                    .expectOnIteration2(new ChainState())
                    .expectOnIteration3(new ChainState())
                    .expectOnIteration4(new ChainState())
                    .expectOnIteration5(new ChainState());
}

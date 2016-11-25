package batchJob;
import main.batchJob.BatchJobExecution;
import main.batchJob.JobEnhancements;
import main.batchJob.SimpleBatchAction;
import main.common.BatchException;
import main.itteration.IterationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.StringJoiner;
import java.util.stream.Collector;

import static batchJob.Constants.AFTER_EACH;
import static batchJob.Constants.AFTER_FIRST;
import static batchJob.Constants.AFTER_LAST;
import static batchJob.Constants.BEFORE_EACH;
import static batchJob.Constants.BEFORE_FIRST;
import static batchJob.Constants.BEFORE_LAST;
import static batchJob.Constants.BETWEEN;
import static batchJob.Constants.ON_EXCEPTION;



/**
 * Created by atrposki on 24-Nov-16.
 */
@RunWith(Theories.class)
public class BatchJobCallsEnhancersInCorrectOrder {


    public static SimpleBatchAction<Integer, String> createWriteToSbAction(StringBuilder sb){
        return new SimpleBatchAction<Integer, String>().act(x -> {
            sb.append(x.toString());
            return x.toString();
        });
    }

    public static TestJobEnhancements createWithDefault(StringBuilder sb) throws BatchException {
        return  new TestJobEnhancements()
        .afterEach(x->sb.append(AFTER_EACH))
        .afterFirst(x->sb.append(AFTER_FIRST))
        .afterLast(x->sb.append(AFTER_LAST))
        .beforeEach(x->sb.append(BEFORE_EACH))
        .beforeLast(x->sb.append(BEFORE_LAST))
        .beforeFirst(x->sb.append(BEFORE_FIRST))
        .onException(x->sb.append(ON_EXCEPTION))
        .between((x,r)->sb.append(BETWEEN));
    }

    @Theory
    public void runTest(TestCase testCase) throws BatchException, IterationException {
        testCase.run();
        Assert.assertEquals(testCase.toString(),testCase.getExpects(),testCase.getSb().toString());
    }




    @DataPoints
    public static TestCase[] testCases = createTestCases();

    private static TestCase[] createTestCases()  {
    try{


    ArrayList<TestCase> tests = new ArrayList<TestCase>();
    tests.add(new TestCase()
            .desc("EmptyActionsIterator_producesEmtyResult")
            .withEnhancements(x->createWithDefault(x))
            .forInput(1)
            .expect(""));


    tests.add(new TestCase()
            .desc("IteratorWithOne_producesCorrectResult")
            .forInput(1)
            .withEnhancements(x->createWithDefault(x))
            .addWriteToSbAction()
            .expect(BEFORE_FIRST+BEFORE_LAST+BEFORE_EACH+"1"+AFTER_FIRST+AFTER_LAST+AFTER_EACH));


    tests.add(new TestCase()
            .desc("IteratorWith2_producesCorrectResult")
            .forInput(1)
            .withEnhancements(x->createWithDefault(x))
            .addWriteToSbAction()
            .addWriteToSbAction()
            .expect(BEFORE_FIRST+BEFORE_EACH+"1"+AFTER_FIRST+AFTER_EACH+BETWEEN+
                    BEFORE_LAST+BEFORE_EACH+"1"+AFTER_LAST+AFTER_EACH));

    tests.add(new TestCase()
            .desc("IteratorWith3_producesCorrectResult")
            .withEnhancements(x->createWithDefault(x))
            .forInput(1)
            .addWriteToSbAction()
            .addWriteToSbAction()
            .addWriteToSbAction()
            .expect(BEFORE_FIRST+BEFORE_EACH+"1"+AFTER_FIRST+AFTER_EACH+BETWEEN+
                    BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                    BEFORE_LAST+BEFORE_EACH+"1"+AFTER_LAST+AFTER_EACH));

    tests.add(new TestCase()
            .desc("IteratorWith4_producesCorrectResult")
            .withEnhancements(x->createWithDefault(x))
            .forInput(1)
            .addWriteToSbAction()
            .addWriteToSbAction()
            .addWriteToSbAction()
            .addWriteToSbAction()
            .expect(BEFORE_FIRST+BEFORE_EACH+"1"+AFTER_FIRST+AFTER_EACH+BETWEEN+
                    BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                    BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                    BEFORE_LAST+BEFORE_EACH+"1"+AFTER_LAST+AFTER_EACH));


    SimpleBatchAction<Integer, String> throwEx = new SimpleBatchAction<Integer, String>().<Exception>actsWithEx(x -> {
        throw new Exception();
    });
    tests.add(new TestCase()
            .desc("IteratorWith4_ExceptionOnSecond")
            .withEnhancements(x->createWithDefault(x))
            .forInput(1)
            .addWriteToSbAction()
            .add(throwEx)
            .addWriteToSbAction()
            .addWriteToSbAction()
            .expect(BEFORE_FIRST+BEFORE_EACH+"1"+AFTER_FIRST+AFTER_EACH+BETWEEN+
                    BEFORE_EACH+ON_EXCEPTION+AFTER_EACH+BETWEEN+
                    BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                    BEFORE_LAST+BEFORE_EACH+"1"+AFTER_LAST+AFTER_EACH));

        tests.add(new TestCase()
                .desc("IteratorWith4_ExceptionOnFirst")
                .withEnhancements(x->createWithDefault(x))
                .forInput(1)
                .add(throwEx)
                .addWriteToSbAction()
                .addWriteToSbAction()
                .addWriteToSbAction()
                .expect(BEFORE_FIRST+BEFORE_EACH+ON_EXCEPTION+AFTER_FIRST+AFTER_EACH+BETWEEN+
                        BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                        BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                        BEFORE_LAST+BEFORE_EACH+"1"+AFTER_LAST+AFTER_EACH));

        tests.add(new TestCase()
                .desc("IteratorWith4_ExceptionOnLast")
                .withEnhancements(x->createWithDefault(x))
                .forInput(1)
                .addWriteToSbAction()
                .addWriteToSbAction()
                .addWriteToSbAction()
                .add(throwEx)
                .expect(BEFORE_FIRST+BEFORE_EACH+"1"+AFTER_FIRST+AFTER_EACH+BETWEEN+
                        BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                        BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                        BEFORE_LAST+BEFORE_EACH+ON_EXCEPTION+AFTER_LAST+AFTER_EACH));

        tests.add( new TestCase()
                .desc("IteratorWith4_skips3rdOnAfterEach")
                .forInput(1)
                .withEnhancements(sb->
                   createWithDefault(sb)
                    .afterEach(y->{
                    if(y.getResult().equals("3")){
                        y.skipAction();
                    }else {
                        sb.append(AFTER_EACH);
                    }
                }))
                .addWriteToSbAction()
                .addWriteToSbAction()
                .add(x ->
                    new SimpleBatchAction<Integer,String>().act(i -> {
                        x.append(3);
                        return "3";
                    })
                )
                .addWriteToSbAction()
                .expect(BEFORE_FIRST+BEFORE_EACH+"1"+AFTER_FIRST+AFTER_EACH+BETWEEN+
                        BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                        BEFORE_EACH+"3"+
                        BEFORE_LAST+BEFORE_EACH+"1"+AFTER_LAST+AFTER_EACH));

        tests.add( new TestCase()
                .desc("IteratorWith4_breaks3rdOnAfterEach")
                .forInput(1)
                .withEnhancements(sb->
                createWithDefault(sb)
                        .afterEach(y->{
                            if(y.getResult().equals("3")){
                                y.breakJob();
                            }else {
                                sb.append(AFTER_EACH);
                            }
                        }))
                .addWriteToSbAction()
                .addWriteToSbAction()
                .add(x ->
                        new SimpleBatchAction<Integer,String>().act(i -> {
                            x.append(3);
                            return "3";
                        })
                )
                .addWriteToSbAction()
                .expect(BEFORE_FIRST+BEFORE_EACH+"1"+AFTER_FIRST+AFTER_EACH+BETWEEN+
                        BEFORE_EACH+"1"+AFTER_EACH+BETWEEN+
                        BEFORE_EACH+"3"));




        TestCase[] asArray = new TestCase[tests.size()];
        int i=0;
        for (TestCase tcase :
                tests) {
            asArray[i] = tcase;
            i++;
        }
        return asArray;
    }catch (Exception e){
     throw new RuntimeException(e);
    }
    }

    public static class counter{
        public int i=1;
    }


}


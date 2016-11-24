

import main.batchJob.interfaces.IBatchAction;
import main.batchJob.interfaces.IBatchActionExecution;
import main.actionsWithBatchException.BatchConsumer;
import main.processing.IBreakConsumer;
import main.batchJob.*;
import main.common.BatchException;
import main.itteration.Chain;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by atrposki on 11/17/2016.
 */

public class asd {
    @Test
    public  void adsads() throws BatchException {

        ArrayList<Integer> lst = new ArrayList<>();
        lst.add(1);
        lst.add(2);
        lst.add(3);
        IBatchAction<Iterator<Integer>,String> test = new IBatchAction<Iterator<Integer>, String>() {
            @Override
            public boolean allow(Iterator<Integer> input) {
                return true;
            }

            @Override
            public boolean breakBefore(Iterator<Integer> input) {
                return false;
            }

            @Override
            public boolean breakAfter(Iterator<Integer> input, String output) {
                return false;
            }

            @Override
            public String accept(Iterator<Integer> in) throws BatchException {
                return in.next()+" ";
            }
        };

        List<IBatchAction<Iterator<Integer>,String>> jobQueue = new ArrayList<IBatchAction<Iterator<Integer>,String>>();
        jobQueue.add(test);
        jobQueue.add(test);
        jobQueue.add(test);


        StringBuilder sb= new StringBuilder();

        IBreakConsumer<Chain<IBatchActionExecution<Iterator<Integer>, String, IBatchAction<Iterator<Integer>, String>>, BatchException>> settings = new JobEnhancements<Iterator<Integer>, String, IBatchAction<Iterator<Integer>, String>>()
                .beforeEach(createConsumer("beforeEach"))
                .beforeFirst(createConsumer("beforeFirst"))
                .beforeLast(createConsumer("beforeLast"))
                .afterEach(createConsumer("afterEach"))
                .afterFirst(createConsumer("afterFirst"))
                .afterLast(createConsumer("afterLast"))
                .buildAsEnhancement();

        BatchJobExecution.<Iterator<Integer>,String, IBatchAction<Iterator<Integer>,String>>Execute(jobQueue.iterator(),lst.iterator(),settings);

    }

    public BeforeEach createConsumer(String debugInfo){
        return  new BeforeEach() {
            @Override
            public String info() {
                return debugInfo;
            }

            @Override
            public void accept(IBatchActionExecution<Iterator<Integer>, String, IBatchAction<Iterator<Integer>, String>> input) throws BatchException {
                System.out.println(debugInfo+"executes:");
                System.out.println(input.getAction().debugInfo());
            }
        };
    }

    public static interface BeforeEach extends BatchConsumer<IBatchActionExecution<Iterator<Integer>, String, IBatchAction<Iterator<Integer>,String>>> {
        String info();
    }
}

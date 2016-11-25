package examples;

import main.batchJob.SimpleBatchAction;
import main.common.FunctionWithException;
import main.itteration.IteratorWithException;
import main.itteration.Transformer;
import main.processing.StreamProcess;

import java.util.Iterator;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class IntegerToActionTransformer extends Transformer<Integer,SimpleBatchAction<Integer,Boolean>,Exception,Exception> {
    public IntegerToActionTransformer(FunctionWithException<Integer, SimpleBatchAction<Integer, Boolean>, Exception> transformFunction, Iterable<Integer> input) {
        super(transformFunction, input);
    }

    public IntegerToActionTransformer(Iterator<Integer> input, FunctionWithException<Integer, SimpleBatchAction<Integer, Boolean>, Exception> transformFunction) {
        super(input, transformFunction);
    }

    public IntegerToActionTransformer(IteratorWithException<Integer, Exception> input, FunctionWithException<Integer, SimpleBatchAction<Integer, Boolean>, Exception> transformFunction) {
        super(input, transformFunction);
    }
}

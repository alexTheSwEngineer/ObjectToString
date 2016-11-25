package main.batchJob;

import main.batchJob.interfaces.IBatchAction;
import main.batchJob.interfaces.IBatchActionExecution;
import main.common.FunctionWithException;
import main.itteration.IterationException;
import main.itteration.IteratorWithException;
import main.processing.IBreakConsumer;
import main.common.BatchException;
import main.itteration.Chain;
import main.itteration.Transformer;

import java.util.Iterator;

/**
 * Created by atrposki on 22-Nov-16.
 */
public class BatchJobExecution<Tin, Tout, TAction extends IBatchAction<Tin, Tout>> {
    private IBreakConsumer<Chain<IBatchActionExecution<Tin, Tout, TAction>, BatchException>> enhancer;
    Iterator<TAction> actions;

    public BatchJobExecution(IBreakConsumer<Chain<IBatchActionExecution<Tin, Tout, TAction>, BatchException>> enhancer, Iterable<TAction> actions) throws BatchException {
        this.actions = actions.iterator();
        this.enhancer = enhancer;
    }

    public void execute(Tin input) throws BatchException, IterationException {
        Execute(new Transformer<TAction,TAction,Exception, Exception>( actions,x->x), input, enhancer);
    }

    public static <Tin, Tout, TAction extends IBatchAction<Tin, Tout>> void Execute(Iterable<TAction> actions, Tin input) throws BatchException {
        if (!actions.iterator().hasNext()) {
            return;
        }
        Iterator<TAction> iterator = actions.iterator();
        while (iterator.hasNext()) {
            TAction action = iterator.next();
            if (action.breakBefore(input)) {
                break;
            }
            if (!action.allow(input)) {
                continue;
            }
            Tout result = action.accept(input);
            if (action.breakAfter(input, result)) {
                break;
            }
        }

    }

    public static <Tin, Tout, TAction extends IBatchAction<Tin, Tout>,ItrEx extends Exception> void Execute(IteratorWithException<TAction,ItrEx> actions, Tin input, IBreakConsumer<Chain<IBatchActionExecution<Tin, Tout, TAction>, BatchException>> enhancer) throws BatchException, IterationException {
        if (!actions.hasNext()) {
            return;
        }

        Transformer<TAction,IBatchActionExecution<Tin,Tout,TAction>,ItrEx,BatchException> actionCallIterator=
        new Transformer<TAction,IBatchActionExecution<Tin,Tout,TAction>,ItrEx,BatchException>(actions,createBatchActionCall(input));
        Chain<IBatchActionExecution<Tin, Tout, TAction>, BatchException> chain =
        new Chain<IBatchActionExecution<Tin, Tout, TAction>, BatchException>(actionCallIterator);
        for (; chain.isPresent(); chain.move()) {
            if (enhancer.breakBeforeExec(chain)) {
                break;
            }

            if (!enhancer.allowExec(chain)) {
                continue;
            }
            enhancer.accept(chain);
            if (enhancer.breakAfterExecc(chain)) {
                break;
            }

        }
    }

    public static <Tin, Tout, TAction extends IBatchAction<Tin, Tout>> FunctionWithException<TAction, IBatchActionExecution<Tin, Tout, TAction>, BatchException>
    createBatchActionCall(Tin input)  {
        return x-> new BatchActionCall<Tin,Tout,TAction>(x,input);
    }


}

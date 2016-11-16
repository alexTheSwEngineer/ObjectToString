package main.actionsWithBatchException;

import main.common.BatchException;
import main.common.FunctionWithException;

/**
 * Created by atrposki on 19-Nov-16.
 */
public interface ITransformableAction<Tin,Tout,TAction extends FunctionWithException<Tin,Tout,BatchException>>  {
    void skipAction();
    void setAction(TAction action);
    void breakJob();
    boolean triedToExecute();
    BatchException getException();
    TAction getAction();
    Tin getInput();
    Tout getOutput();
}

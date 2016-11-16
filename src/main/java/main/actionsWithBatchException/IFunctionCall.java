package main.actionsWithBatchException;

import main.common.BatchException;
import main.common.FunctionWithException;
import main.processing.IStreamableAction;

/**
 * Created by atrposki on 19-Nov-16.
 */
public interface IFunctionCall<Tin,Tout,Tfunct extends FunctionWithException<Tin,Tout,BatchException>>
        extends ITransformableAction<Tin,Tout,Tfunct>,
        IStreamableAction {
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.batchJob.interfaces;

import main.actionsWithBatchException.ITransformableAction;
import main.common.BatchException;
import main.common.FunctionWithException;
import main.processing.IStreamableAction;

/**
 *
 *
 */
public interface IBatchActionExecution<Tin,Tout,TAction extends FunctionWithException<Tin, Tout,BatchException>> extends
        IStreamableAction,
        ITransformableAction<Tin,Tout,TAction>
{
    Tout getResult();
    BatchException getException();
    void setExcption(BatchException e);
}

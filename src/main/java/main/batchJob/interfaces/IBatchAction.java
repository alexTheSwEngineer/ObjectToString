package main.batchJob.interfaces;


import main.common.BatchException;
import main.common.FunctionWithException;
import main.processing.IBreakableFunction;

/**
 * Created by atrposki on 22-Nov-16.
 */
public interface IBatchAction<Tin,Tout> extends IBreakableFunction<Tin,Tout,BatchException> {

}

package main.batchJob.interfaces;


import main.common.BatchException;
import main.common.FunctionWithException;

/**
 * Created by atrposki on 22-Nov-16.
 */
public interface IBatchAction<Tin,Tout> extends FunctionWithException<Tin,Tout,BatchException> {
    boolean allow(Tin input);
    boolean breakBefore(Tin input);
    boolean breakAfter(Tin input,Tout output);

    default String debugInfo(){
        return this.toString();
    }
}

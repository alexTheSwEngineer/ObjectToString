package main.processing;

import main.common.FunctionWithException;

import java.util.function.Function;

/**
 * Created by atrposki on 25-Nov-16.
 */
public interface IBreakableFunction<Tin,Tout,TEx extends Exception> extends FunctionWithException<Tin,Tout,TEx> {
    boolean allow(Tin input);
    boolean breakBefore(Tin input);
    boolean breakAfter(Tin input,Tout output);
    default String debugInfo(){
        return this.toString();
    }
}

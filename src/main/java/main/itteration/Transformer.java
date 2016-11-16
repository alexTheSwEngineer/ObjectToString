package main.itteration;

import main.common.FunctionWithException;

import java.util.Iterator;

/**
 * Created by atrposki on 22-Nov-16.
 */
public class Transformer<Tin,Tout,TException extends Exception> implements IteratorWithException<Tout,TException> {
    private FunctionWithException<Tin,Tout,TException> transformFunction;
    private IteratorWithException<Tin,TException> input;
    public Transformer(FunctionWithException<Tin,Tout,TException> transformFunction,Iterable<Tin> input){
        this(input.iterator(),transformFunction);
    }

    public Transformer(Iterator<Tin> input,FunctionWithException<Tin,Tout,TException> transformFunction){
        this.transformFunction=transformFunction;
        this.input = new IteratorWithException<Tin, TException>() {
            @Override
            public boolean hasNext() {
                return input.hasNext();
            }

            @Override
            public Tin next() throws TException {
                return input.next();
            }
        };
    }

    public Transformer(IteratorWithException<Tin,TException> input,FunctionWithException<Tin,Tout,TException> transformFunction){
        this.transformFunction=transformFunction;
        this.input = input;
    }



    public boolean hasNext(){
        return input.hasNext();
    }

    public Tout next() throws TException{
        return transformFunction.accept(input.next());
    }
}

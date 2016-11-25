package main.itteration;

import main.common.FunctionWithException;

import java.util.Iterator;

/**
 * Created by atrposki on 22-Nov-16.
 */
public class Transformer<Tin,Tout, TItrException extends Exception,TTransformException extends Exception> implements IteratorWithException<Tout, TTransformException> {
    private FunctionWithException<Tin,Tout, TTransformException> transformFunction;
    private IteratorWithException<Tin, TItrException> input;
    public Transformer(FunctionWithException<Tin,Tout, TTransformException> transformFunction, Iterable<Tin> input){
        this(input.iterator(),transformFunction);
    }

    public Transformer(Iterator<Tin> input,FunctionWithException<Tin,Tout, TTransformException> transformFunction){
        this.transformFunction=transformFunction;
        this.input = new IteratorWithException<Tin, TItrException>() {
            @Override
            public boolean hasNext() {
                return input.hasNext();
            }

            @Override
            public Tin next() throws TItrException {
                return input.next();
            }
        };
    }

    public Transformer(IteratorWithException<Tin, TItrException> input, FunctionWithException<Tin,Tout, TTransformException> transformFunction){
        this.transformFunction=transformFunction;
        this.input = input;
    }



    public boolean hasNext(){
        return input.hasNext();
    }

    public Tout next() throws  IterationException, TTransformException {

        Tin next=null;
        try {
             next= input.next();
        }catch (Exception e){
            throw new IterationException(e);
        }


        return transformFunction.accept(next);
    }
}

package main.batchJob;

import main.batchJob.interfaces.IBatchAction;
import main.common.BatchException;
import main.common.BiFunctionWithException;
import main.common.FunctionWithException;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by atrposki on 24-Nov-16.
 */
public class SimpleBatchAction<Tin,Tout> implements IBatchAction<Tin,Tout> {

    private FunctionWithException<Tin,Boolean,BatchException> accepts= x->true;
    private FunctionWithException<Tin,Boolean,BatchException> breakBefore=x->false;
    private FunctionWithException<Tin,Tout,BatchException> action=x->null;
    private BiFunctionWithException<Tin,Tout,Boolean,BatchException> breakAfter=(x,y)->false;

    public SimpleBatchAction(){

    }

    @Override
    public Tout accept(Tin in) throws BatchException {
        return action.accept(in);
    }

    @Override
    public boolean allow(Tin input) {

        try {
            return accepts.accept(input);
        } catch (BatchException e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public boolean breakBefore(Tin input) {

        try {
            return breakBefore.accept(input);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public boolean breakAfter(Tin input, Tout output) {

        try {
           return this.breakAfter.accept(input,output);
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public <TEx extends Exception> SimpleBatchAction<Tin,Tout> actsWithEx(FunctionWithException<Tin,Tout,TEx> f){
        this.action=x->{
            try{
                return f.accept(x);
            }catch (Exception ex){
                throw new BatchException(ex);
            }
        };
        return this;
    }

    public  SimpleBatchAction<Tin,Tout> act(Function<Tin,Tout> f){
        this.action=x->{
            try{
                return f.apply(x);
            }
            catch (Exception ex){
                throw new BatchException(ex);
            }
        };
        return this;
    }

    public SimpleBatchAction<Tin,Tout> breakBefore(Function<Tin,Boolean> f){
        this.breakBefore=x->{
            try{
                return f.apply(x);
            }catch (Exception e){
                throw  new BatchException(e);
            }
        };
        return this;
    }

    public  <TEx extends Exception>SimpleBatchAction<Tin,Tout> breakBeforeWithEx(FunctionWithException<Tin,Boolean,TEx> f){
        this.breakBefore=x->{
            try{
                return f.accept(x);
            }catch (Exception e){
                throw  new BatchException(e);
            }
        };
        return this;
    }

    public SimpleBatchAction<Tin,Tout> breakAfter(BiFunction<Tin,Tout,Boolean> f){
        this.breakAfter=(x,y)->{
            try{
                return f.apply(x,y);
            }catch (Exception e){
                throw  new BatchException(e);
            }
        };
        return this;
    }

    public <TEx extends Exception> SimpleBatchAction<Tin,Tout> breakAfterWithEx(BiFunctionWithException<Tin,Tout,Boolean,TEx> f){
        this.breakAfter=(x,y)->{
            try{
                return f.accept(x,y);
            }catch (Exception e){
                throw  new BatchException(e);
            }
        };
        return this;
    }
}

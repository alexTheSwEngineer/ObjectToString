package main.batchJob;

import main.batchJob.interfaces.IBatchAction;
import main.common.BatchException;
import main.common.FunctionWithException;

import java.util.function.Function;

/**
 * Created by atrposki on 24-Nov-16.
 */
public class SimpleBatchAction<Tin,Tout> implements IBatchAction<Tin,Tout> {

    private FunctionWithException<Tin,Tout,BatchException> action;
    public SimpleBatchAction(FunctionWithException<Tin,Tout,BatchException> f){
        if(f==null){
            throw new IllegalArgumentException("function must not be null");
        }
        action=f;
    }

    @Override
    public Tout accept(Tin in) throws BatchException {
        return action.accept(in);
    }

    @Override
    public boolean allow(Tin input) {
        return true;
    }

    @Override
    public boolean breakBefore(Tin input) {
        return false;
    }

    @Override
    public boolean breakAfter(Tin input, Tout output) {
        return false;
    }

    public static <Tin,Tout,Tex extends Exception> SimpleBatchAction<Tin,Tout> createWithEx(FunctionWithException<Tin,Tout,Tex> f){
        return new SimpleBatchAction<Tin, Tout>(x->{
            try{
                return f.accept(x);
            }catch (Exception ex){
                throw new BatchException(ex);
            }
        });
    }

    public static <Tin,Tout> SimpleBatchAction<Tin,Tout> create(Function<Tin,Tout> f){
        return new SimpleBatchAction<Tin, Tout>(x->{
            try{
                return f.apply(x);
            }catch (Exception ex){
                throw new BatchException(ex);
            }
        });
    }
}

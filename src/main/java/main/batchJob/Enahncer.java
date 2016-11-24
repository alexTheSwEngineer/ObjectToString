package main.batchJob;

import main.actionsWithBatchException.BatchConsumer;
import main.batchJob.interfaces.IBatchActionExecution;
import main.common.FunctionWithException;
import main.common.BatchException;
import main.itteration.Chain;
import main.processing.IBreakConsumer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by atrposki on 20-Nov-16.
 */
public class Enahncer<Tin,Tout,TAction extends FunctionWithException<Tin,Tout,BatchException>> implements IBreakConsumer<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>> {

    private String debugInfo;
    List<FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,BatchException>> allowConditions=new ArrayList<>();
    List<FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,BatchException>> breakBeforeConditions=new ArrayList<>();
    List<FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,BatchException>> breakAfterConditions=new ArrayList<>();
    BatchConsumer<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>> decorator= x->{};


    public Enahncer(String debugInfo) {
        this.debugInfo=debugInfo;
    }

    public Enahncer<Tin,Tout,TAction> breakBeforeIf(FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,BatchException> condition){
        breakBeforeConditions.add(condition);
        return this;
    }

    public Enahncer<Tin,Tout,TAction> breakAfterIf(FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,BatchException> condition){
        breakAfterConditions.add(condition);
        return this;
    }

    public Enahncer<Tin,Tout,TAction> act(BatchConsumer<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>> action) throws BatchException {
        if(action!=null){
            this.decorator = action;
        }else{
            this.decorator=x->{};
        }

        return this;
    }

    public <TEx extends Exception> Enahncer<Tin,Tout,TAction> whenSafe(FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,TEx> condition) {
        return when((Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>x) -> {
            try {
                return condition.accept(x);
            } catch (Exception e) {
                throw new BatchException(e);
            }
        });
    }

    public Enahncer<Tin,Tout,TAction> when(FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,BatchException> condition) {
        allowConditions.add(condition);
        return this;
    }

    public String toString(){
        return  this.debugInfo;
    }

    @Override
    public void accept(Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException> in) throws BatchException {
         decorator.accept(in);
    }

    public boolean findAny(List<FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,BatchException>> conditions,boolean conditionValue,Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException> input) throws BatchException {
        for (FunctionWithException<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>,Boolean,BatchException>
            condition:conditions) {
            if(condition.accept(input)==conditionValue){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean breakBeforeExec(Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException> c) throws BatchException {
        return  findAny(breakBeforeConditions,true,c);
    }

    @Override
    public boolean allowExec(Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException> c) throws BatchException {
        return !findAny(allowConditions,false,c);
    }

    @Override
    public boolean breakAfterExecc(Chain<IBatchActionExecution<Tin, Tout, TAction>,BatchException> c) throws BatchException {
        return findAny(breakAfterConditions,true,c);
    }

}

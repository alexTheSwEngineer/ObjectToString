package main.batchJob;

import main.batchJob.interfaces.IBatchAction;
import main.batchJob.interfaces.IBatchActionExecution;
import main.common.BatchException;

/**
 * Created by atrposki on 20-Nov-16.
 */
public class BatchActionCall<Tin,Tout,TAction extends IBatchAction<Tin,Tout>> implements IBatchActionExecution<Tin,Tout,TAction> {

    private TAction action;
    private Tin input;
    private Tout output;
    private BatchException exception;
    private boolean forceSkip;
    private boolean forceBreak;
    private boolean triedToExecute;

    public BatchActionCall(TAction action,Tin input){
        this.action=action;
        this.input = input;
    }


    @Override
    public boolean breakBeforeExec() throws BatchException {
        return action.breakBefore(this.input)||forceBreak;
    }

    @Override
    public boolean allowExec() throws BatchException {
        return action.allow(this.input)&&!forceSkip;
    }

    @Override
    public boolean breakAfterExecc() throws BatchException {
        return action.breakAfter(input,output)||forceBreak;
    }

    @Override
    public void execute() throws BatchException {
        try{
            this.triedToExecute=true;
            output = action.accept(input);
        }
        catch (BatchException e){
            this.exception =e;
            return;
        }
        catch (Exception e){
            this.exception = new BatchException(e);
            return;
        }
    }

    @Override
    public void skipAction() {
        this.forceSkip=true;
    }

    @Override
    public void setAction(TAction action) {
            this.action=action;
    }

    @Override
    public void breakJob() {
        this.forceBreak=true;
    }

    @Override
    public boolean triedToExecute() {
        return false;
    }

    @Override
    public TAction getAction() {

        return action;
    }

    @Override
    public Tin getInput() {

        return input;
    }

    @Override
    public Tout getOutput() {
        return output;
    }



    @Override
    public Tout getResult() {
        return output;
    }

    @Override
    public BatchException getException() {
        return exception;
    }

    @Override
    public void setExcption(BatchException e) {
        this.exception=e;
    }


    public String toString(){
        return "Curred action: "+action.debugInfo();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.batchJob;
import main.actionsWithBatchException.BatchBiConsumer;
import main.actionsWithBatchException.BatchConsumer;
import main.batchJob.interfaces.IBatchAction;
import main.batchJob.interfaces.IBatchActionExecution;
import main.common.FunctionWithException;
import main.processing.IBreakConsumer;
import main.common.BatchException;
import main.itteration.Chain;
import main.processing.FunctionProcess;

/**
 *
 * @author Aleksandar
 */
public class JobEnhancements<Tin,Tout,TAction extends IBatchAction<Tin,Tout>> {
    private BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> afterEach  = JobEnhancements::empty;
    private BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> beforeFirst  = JobEnhancements::empty;
    private BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> afterFirst  = JobEnhancements::empty;
    private BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> beforeLast = JobEnhancements::empty;
    private BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> afterLast  = JobEnhancements::empty;
    private BatchBiConsumer<IBatchActionExecution<Tin, Tout, TAction>> between= JobEnhancements::empty;
    private BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> onException = JobEnhancements::empty;
    private BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> beforeEach =JobEnhancements::empty;



    public BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> getBeforeEach() {
        return beforeEach;
    }
    public BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> getAfterEach() {
        return afterEach;
    }

    public BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> getBeforeFirst() {
        return beforeFirst;
    }

    public BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> getAfterFirst() {
        return afterFirst;
    }

    public BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> getBeforeLast() {
        return beforeLast;
    }

    public BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> getAfterLast() {
        return afterLast;
    }

    public BatchBiConsumer<IBatchActionExecution<Tin, Tout, TAction>> getBetween() {
        return between;
    }

    public BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> getOnException() {
        return onException;
    }

    public JobEnhancements() throws BatchException {
        this.afterEach  = JobEnhancements::empty;
        this.beforeFirst  = JobEnhancements::empty;
        this.afterFirst  = JobEnhancements::empty;
        this.beforeLast = JobEnhancements::empty;
        this.afterLast  = JobEnhancements::empty;
        this.between= JobEnhancements::empty;
        this.onException = JobEnhancements::empty;
        this.beforeEach = JobEnhancements::empty;
    }
    public JobEnhancements(JobEnhancements<Tin,Tout,TAction> other) throws BatchException {
        this.afterEach=other.afterEach;
        this.beforeFirst=other.beforeFirst;
        this.afterFirst=other.afterFirst;
        this.beforeLast=other.beforeLast;
        this.afterLast=other.afterLast;
        this.between=other.between;
        this.onException=other.onException;
        this.beforeEach = other.beforeEach;
    }





    public JobEnhancements<Tin,Tout,TAction> beforeFirst(BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> f) throws BatchException {
        JobEnhancements<Tin,Tout,TAction> newJob = new JobEnhancements<Tin, Tout, TAction>(this);
        newJob.beforeFirst=f;
        return newJob;
    }
    public JobEnhancements<Tin,Tout,TAction> beforeLast(BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> f) throws BatchException {
        JobEnhancements<Tin,Tout,TAction> newJob = new JobEnhancements<Tin, Tout, TAction>(this);
        newJob.beforeLast=f;
        return newJob;
    }
    public JobEnhancements<Tin,Tout,TAction> afterEach(BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> f) throws BatchException {
        JobEnhancements<Tin,Tout,TAction> newJob = new JobEnhancements<Tin, Tout, TAction>(this);
        newJob.afterEach=f;
        return newJob;
    }
    public JobEnhancements<Tin,Tout,TAction> afterFirst(BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> f) throws BatchException {
        JobEnhancements<Tin,Tout,TAction> newJob = new JobEnhancements<Tin, Tout, TAction>(this);
        newJob.afterFirst=f;
        return newJob;
    }
    public JobEnhancements<Tin,Tout,TAction> afterLast(BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> f) throws BatchException {
        JobEnhancements<Tin,Tout,TAction> newJob = new JobEnhancements<Tin, Tout, TAction>(this);
        newJob.afterLast=f;
        return newJob;
    }
    public JobEnhancements<Tin,Tout,TAction> onException(BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> f) throws BatchException {
        JobEnhancements<Tin,Tout,TAction> newJob = new JobEnhancements<Tin, Tout, TAction>(this);
        newJob.onException=f;
        return newJob;
    }
    public JobEnhancements<Tin,Tout,TAction> between(BatchBiConsumer<IBatchActionExecution<Tin, Tout, TAction>> f) throws BatchException {
        JobEnhancements<Tin,Tout,TAction> newJob = new JobEnhancements<Tin, Tout, TAction>(this);
        newJob.between=f;
        return newJob;
    }

    public IBreakConsumer<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>> buildAsEnhancement() throws BatchException {
        return new FunctionProcess<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>>(true)
                .add(beforeEnhancement())
                .add(executeEnhancment())
                .add(afterEnhancement())
                .add(new Enahncer<Tin, Tout, TAction>("BreakAfter")
                        .breakAfterIf(c->c.get().breakAfterExecc()||c.get().breakBeforeExec()));
    }

    public JobEnhancements<Tin,Tout,TAction> execute(Iterable<TAction> actions,Tin input) throws BatchException {
          BatchJobExecution.Execute(actions.iterator(),input,buildAsEnhancement());
        return this;
    }

    private static <Tin,Tout,TAction extends FunctionWithException<Tin,Tout,BatchException>>
    void empty(IBatchActionExecution<Tin, Tout, TAction> actionExecution) {}

    private static <Tin,Tout,TAction extends FunctionWithException<Tin,Tout,BatchException>>
    void empty(IBatchActionExecution<Tin, Tout, TAction> l,IBatchActionExecution<Tin, Tout, TAction> r) {}



    private IBreakConsumer<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>> skipNotAllowed =
            new FunctionProcess<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>>(true)
                    .add(enhancer("SkipIfNorAllowed")
                        .breakBeforeIf(c->!c.isPresent())
                        .breakBeforeIf(c->!c.get().allowExec())
                        .breakBeforeIf(c->c.get().breakBeforeExec()));

    private  IBreakConsumer<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>>
    beforeEnhancement() throws BatchException {
        return  new FunctionProcess<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>>(false)
                .add(skipNotAllowed)
                .add(enhancer("beforeFIrst")
                    .when(c->c.isFirst())
                    .act(c-> getBeforeFirst().accept(c.get())))
                .add(enhancer("beforeLast")
                    .when(c->c.isLast())
                    .act(c->getBeforeLast().accept(c.get())))
                .add(enhancer("beforeEach")
                        .act(x->getBeforeEach().accept(x.get())));
    }

    private  IBreakConsumer<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>>
    executeEnhancment() throws BatchException {
        return new Enahncer<Tin, Tout, TAction>("Execution enhancer")
        .when(x->x.get().allowExec())
        .when(x->!x.get().breakBeforeExec())
        .act(x->x.get().execute())
        .breakBeforeIf(x->x.get().breakBeforeExec())
        .breakAfterIf(x->x.get().breakBeforeExec()||x.get().breakAfterExecc());
    }

    private  IBreakConsumer<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>>
    afterEnhancement() throws BatchException {
        return new FunctionProcess<Chain<IBatchActionExecution<Tin,Tout,TAction>,BatchException>>(false)
                .add(enhancer("onException")
                    .when(c->c.get().getException()!=null)
                    .act(c->onException.accept(c.get()))
                    .breakAfterIf(c->c.get().breakAfterExecc()))
                .add(enhancer("breakAfter")
                     .breakAfterIf(c->c.get().breakAfterExecc()))
                .add(skipNotAllowed)
                .add(enhancer("afterFIrst")
                    .when(c->c.isFirst())
                    .act(c-> getAfterFirst().accept(c.get())))
                .add(enhancer("afterLast")
                    .when(c->c.isLast())
                    .act(c-> getAfterLast().accept(c.get())))
                .add(enhancer("afterall")
                        .act(c-> getAfterEach().accept(c.get())))
                .add(enhancer("between")
                    .when(c->c.isBetween())
                    .act(c-> getBetween().accept(c.get(),c.getNext())));
    }

    private Enahncer<Tin,Tout,TAction> enhancer(String debugInfo){
        return  new Enahncer<Tin,Tout,TAction>(debugInfo)
                .breakBeforeIf(x->x.get().breakBeforeExec()||!x.get().allowExec());
    }


    public JobEnhancements<Tin, Tout, TAction> beforeEach(BatchConsumer<IBatchActionExecution<Tin, Tout, TAction>> f) throws BatchException {
        JobEnhancements<Tin,Tout,TAction> newJob = new JobEnhancements<Tin, Tout, TAction>(this);
        newJob.beforeEach = f;
        return newJob;
    }
}

























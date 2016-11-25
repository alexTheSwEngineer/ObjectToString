/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.processing;

import main.common.BatchException;
import main.itteration.IterationException;
import main.itteration.IteratorWithException;

/**
 *
 * @author Aleksandar
 */
public class StreamProcess implements IStreamableAction {
    private IteratorWithException<IStreamableAction,BatchException> iterator;
    private boolean propagateBreak;
    private boolean shouldBreak;

    public StreamProcess(IteratorWithException<IStreamableAction,BatchException> iterator,boolean propagateBreak)
    {
       this.propagateBreak=propagateBreak;
       this.iterator=iterator;
    }

    @Override
    public boolean breakBeforeExec() throws BatchException {
        return false;
    }

    @Override
    public boolean allowExec() throws BatchException {
        return true;
    }

    @Override
    public boolean breakAfterExecc() throws BatchException {
        return this.propagateBreak && this.shouldBreak;
    }

    @Override
    public void execute() throws BatchException {
        while (iterator.hasNext()){
            IStreamableAction action = null;
            try {
                action = iterator.next();
            } catch (IterationException e) {
                throw new BatchException(e);
            }
            if(action.breakBeforeExec()){
                this.shouldBreak=true;
                break;
            }

            if(action.allowExec()){
                action.execute();
            }

            if(action.breakAfterExecc()){
                this.shouldBreak=true;
                break;
            }
        }
    }
}

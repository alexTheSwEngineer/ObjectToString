/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.processing;

import main.common.BatchException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandar
 */
public class FunctionProcess<Tin> implements IBreakConsumer<Tin>
{
    private List<IBreakConsumer<Tin>> actions = new ArrayList<IBreakConsumer<Tin>>();
    private boolean shouldBreak = false;
    private boolean breakPropagates = false;

    public FunctionProcess(Boolean breakPropagates) {
        this.breakPropagates=breakPropagates;
    }
    public boolean breakBeforeExec() throws BatchException {
        return false;
    }

    public boolean allowExec() throws BatchException {
        return true;
    }

    public boolean breakAfterExecc() throws BatchException {
        return shouldBreak && breakPropagates;
    }


    public FunctionProcess<Tin> add(IBreakConsumer<Tin> action) throws BatchException {
        actions.add(action);
        return this;
    }

    public String toString(){
        return "Batch processs with" + actions.stream().filter(x->x!=null).map(x->x.toString()).reduce("",(l,r)->l+" "+r) ;
    }


    @Override
    public boolean breakBeforeExec(Tin in) throws BatchException {
        return false;
    }

    @Override
    public boolean allowExec(Tin in) throws BatchException {
        return true;
    }

    @Override
    public boolean breakAfterExecc(Tin tin) throws BatchException {
        return shouldBreak&&breakPropagates;
    }

    @Override
    public void accept(Tin input) throws BatchException {
        for (IBreakConsumer<Tin> action:actions) {
            boolean breakExec = action.breakBeforeExec(input);
            boolean skip = !action.allowExec(input);

            if(action.breakBeforeExec(input)){
                this.shouldBreak=true;
                break;
            }

            if(!action.allowExec(input)){
                continue;
            }

            action.accept(input);

            if(action.breakAfterExecc(input)){
                this.shouldBreak=true;
                break;
            }
        }
    }
}

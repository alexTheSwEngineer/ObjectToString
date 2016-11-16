package main.processing;

import main.common.BatchException;

/**
 * Created by atrposki on 22-Nov-16.
 */
public interface IBreakConsumer<Tin> {
    boolean breakBeforeExec(Tin in) throws BatchException;
    boolean allowExec(Tin in) throws BatchException ;
    boolean breakAfterExecc(Tin tin) throws BatchException ;
    void accept(Tin in) throws BatchException;
}

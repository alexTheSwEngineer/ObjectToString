package main.processing;

import main.common.BatchException;

/**
 * Created by atrposki on 19-Nov-16.
 */
public interface IStreamableAction
{
    boolean breakBeforeExec()   throws BatchException;
    boolean allowExec()         throws BatchException;
    boolean breakAfterExecc() throws BatchException;
    void execute() throws BatchException;
}

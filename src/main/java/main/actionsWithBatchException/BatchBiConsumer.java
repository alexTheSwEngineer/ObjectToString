package main.actionsWithBatchException;

import main.common.BatchException;

/**
 * Created by atrposki on 21-Nov-16.
 */
public interface BatchBiConsumer<T> {
    void accept(T l,T r)throws BatchException;
}

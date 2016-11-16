package main.actionsWithBatchException;

import main.common.BatchException;

/**
 * Created by atrposki on 20-Nov-16.
 */
public interface BatchConsumer<T> {
    void accept(T input)throws BatchException;
}

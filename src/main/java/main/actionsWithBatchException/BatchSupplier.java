package main.actionsWithBatchException;

import main.common.BatchException;

/**
 * Created by atrposki on 21-Nov-16.
 */
@FunctionalInterface
public interface BatchSupplier<T> {
    T get()throws BatchException;
}

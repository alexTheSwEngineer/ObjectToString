package main.itteration;

import main.common.BatchException;

/**
 * Created by atrposki on 22-Nov-16.
 */
public interface IteratorWithException<T,Tex extends Exception> {
    boolean hasNext();
    T next() throws Tex, IterationException;
}

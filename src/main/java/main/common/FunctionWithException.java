package main.common;

/**
 * Created by atrposki on 21-Nov-16.
 */
public interface FunctionWithException<Tin,Tout,Tex extends Exception> {
    Tout accept(Tin in) throws Tex;
}

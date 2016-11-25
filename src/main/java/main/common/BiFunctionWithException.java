package main.common;

/**
 * Created by atrposki on 25-Nov-16.
 */
public interface BiFunctionWithException<Tin1,Tin2,Tout,TEx extends Exception> {
    Tout accept(Tin1 in1,Tin2 tin2) throws TEx;
}

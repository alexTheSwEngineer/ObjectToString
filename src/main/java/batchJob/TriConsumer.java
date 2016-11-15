package batchJob;

/**
 * Created by atrposki on 11/15/2016.
 */
@FunctionalInterface
public interface TriConsumer<T1,T2,T3> {
    void apply(T1 p1,T2 p2,T3 p3);
}

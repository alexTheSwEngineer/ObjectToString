package common;

/**
 * Created by atrposki on 24-Nov-16.
 */
public interface TriConsumer<T1,T2,T3> {
    void accept(T1 t1,T2 t2, T3 t3);
}

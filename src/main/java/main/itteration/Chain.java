package main.itteration;


import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

/**
 * Created by atrposki on 21-Nov-16.
 */
public class Chain<T,TEx extends Exception> {
    private Optional<T> current;
    private Optional<T> prev;
    private Optional<T> next;
    private IteratorWithException<Optional<T>,TEx> itr;

    public Chain(IteratorWithException<T,TEx> inputItr) throws TEx {
        itr = new Transformer<T,Optional<T>,TEx>(inputItr, this::optionalOfT);
        if (inputItr.hasNext()) {
            current = Optional.ofNullable(inputItr.next());
        }
        if (inputItr.hasNext()) {
            next = Optional.ofNullable(inputItr.next());
        }
    }

    public Chain(Iterator<T> inputItr) {
        itr =new Transformer<T,Optional<T>,TEx>(inputItr,this::optionalOfT);
        if (inputItr.hasNext()) {
            current = Optional.ofNullable(inputItr.next());
        }
        if (inputItr.hasNext()) {
            next = Optional.ofNullable(inputItr.next());
        }
    }

    public Chain(Collection<T> collection) {

    }

    public boolean isPresent() {
        return current != null;
    }

    public boolean isFirst() {
        return isPresent() && prev == null;
    }

    public boolean isLast() {
        return isPresent() && next == null;
    }

    public boolean isBetween() {
        return isPresent() && !isLast();
    }

    public T get() {
        return current == null ? null : current.orElse(null);
    }

    public T getPrev() {
        return prev.orElse(null);
    }

    public T getNext() {
        return next.orElse(null);
    }

    public Chain<T,TEx> move() throws TEx {

        prev = current;
        current = next;
        if (itr.hasNext()) {
            next = itr.next();
        } else {
            next = null;
        }

        return this;
    }

    private Optional<T> optionalOfT(T in)throws TEx{
        return Optional.ofNullable(in);
    }

}

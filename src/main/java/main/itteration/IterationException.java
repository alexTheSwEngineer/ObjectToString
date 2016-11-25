package main.itteration;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class IterationException extends Exception {
    public IterationException(Exception e){
        super(e);
    }

    public  IterationException(){
        super();
    }
}

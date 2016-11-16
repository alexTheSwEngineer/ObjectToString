package chain;

import common.TriConsumer;
import main.itteration.Chain;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by atrposki on 23-Nov-16.
 */

public class TestCase<T,Q>{
    private String description;
    private Iterable<Q> iterable;

    public Optional<T> expectedState1;
    public Optional<T> expectedState2;
    public Optional<T> expectedState3;
    public Optional<T> expectedState4;
    public Optional<T> expectedState5;

    public TestCase(String desc){
        this.description = desc;
    }

    public T getExpectedState1() {
        return expectedState1.orElse(null);
    }

    public T getExpectedState2() {
        return expectedState2.orElse(null);
    }

    public T getExpectedState3() {
        return expectedState3.orElse(null);
    }

    public T getExpectedState4() {
        return expectedState4.orElse(null);
    }

    public T getExpectedState5() {
        return expectedState5.orElse(null);
    }

    public TestCase<T,Q> given(Iterable<Q> itr){
        this.iterable=itr;
        return this;
    }

    public TestCase expectOnIteration1(T g){
        this.expectedState1=Optional.ofNullable(g);
        return this;
    }
    public TestCase expectOnIteration2(T g){
        this.expectedState2=Optional.ofNullable(g);
        return this;
    }
    public TestCase expectOnIteration3(T g){
        this.expectedState3=Optional.ofNullable(g);
        return this;
    }
    public TestCase expectOnIteration4(T g){
        this.expectedState4=Optional.ofNullable(g);
        return this;
    }
    public TestCase expectOnIteration5(T g){
        this.expectedState5=Optional.ofNullable(g);
        return this;
    }

    public Iterable<Q> given() {
        return this.iterable;
    }

    public String toString(){
        return  description;
    }


    public static <T,Q,Tex extends Exception> void run( TestCase<Q,T> testCase, TriConsumer<Chain<T,Tex>,Q,String> assertion) throws Exception {
        Chain<T,Tex> chain = new Chain<T, Tex>(testCase.given().iterator());


        if(testCase.expectedState1==null){
            throw new Exception("No test cases in test case "+testCase.toString());
        }
        assertion.accept(chain,testCase.getExpectedState1()," first iteration of: "+testCase.toString());
        chain.move();


        if(testCase.expectedState2==null){
            return;
        }
        assertion.accept(chain,testCase.getExpectedState2(),"second iteration of:"+testCase.toString());
        chain.move();


        if(testCase.expectedState3==null){
            return;
        }
        assertion.accept(chain,testCase.getExpectedState3(),"htird iteration of:"+testCase.toString());
        chain.move();


        if(testCase.expectedState4==null){
            return;
        }
        assertion.accept(chain,testCase.getExpectedState4(),"fourht iteration of:"+testCase.toString());
        chain.move();


        if(testCase.expectedState5==null){
            return;
        }
        assertion.accept(chain,testCase.getExpectedState5(),"fifth iteration of:"+testCase.toString());
        chain.move();
    }
}

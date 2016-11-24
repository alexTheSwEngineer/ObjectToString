package batchJob;

import main.batchJob.BatchJobExecution;
import main.batchJob.JobEnhancements;
import main.batchJob.SimpleBatchAction;
import main.common.BatchException;
import main.common.FunctionWithException;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by atrposki on 24-Nov-16.
 */
public class TestCase {
    private String description;
    private ArrayList<SimpleBatchAction<Integer,String>> actions = new ArrayList<>();
    private JobEnhancements<Integer, String, SimpleBatchAction<Integer, String>> enhancements;

    public String getExpects() {
        return expects;
    }

    private String expects;
    private StringBuilder sb = new StringBuilder();

    public Integer getInput() {
        return input;
    }

    private Integer input;

    public ArrayList<SimpleBatchAction<Integer, String>> getActions() {
        return actions;
    }

    public StringBuilder getSb() {
        return sb;
    }

    public TestCase desc(String dsc){
        this.description = dsc;
        return this;
    }

    public TestCase add(SimpleBatchAction<Integer,String> action){
        actions.add(action);
        return this;
    }

    public <TEx extends Exception>  TestCase add(FunctionWithException<StringBuilder,SimpleBatchAction<Integer,String>,TEx> actionProducer) throws TEx{
        actions.add(actionProducer.accept(this.sb));
        return this;
    }

    public TestCase addWriteToSbAction(){
        actions.add(SimpleBatchAction.<Integer, String>create(x -> {
            sb.append(x.toString());
            return x.toString();
        }));
        return this;
    }


    public TestCase expect(String str){
        this.expects= str;
        return this;
    }

    public TestCase forInput(Integer input){
        this.input = input;
        return this;
    }
    public String toString(){
        return  this.description;
    }
    public <TEx extends Exception>  TestCase withEnhancements(FunctionWithException<StringBuilder,JobEnhancements<Integer,String,SimpleBatchAction<Integer,String>>,TEx> producer) throws TEx{
        this.enhancements = producer.accept(this.sb);
        return this;
    }

    public void run(JobEnhancements<Integer,String,SimpleBatchAction<Integer,String>> settings) throws BatchException {
        BatchJobExecution.<Integer,String,SimpleBatchAction<Integer,String>>Execute(actions.iterator(),input,settings.build());
    }
}

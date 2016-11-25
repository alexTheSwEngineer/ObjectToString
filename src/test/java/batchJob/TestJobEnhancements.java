package batchJob;

import main.batchJob.JobEnhancements;
import main.batchJob.SimpleBatchAction;
import main.common.BatchException;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class TestJobEnhancements extends JobEnhancements<Integer,String,SimpleBatchAction<Integer,String>,TestJobEnhancements> {
    protected TestJobEnhancements() throws BatchException {
    }

    protected TestJobEnhancements(JobEnhancements<Integer, String, SimpleBatchAction<Integer, String>, TestJobEnhancements> other) throws BatchException {
        super(other);
    }

    @Override
    public TestJobEnhancements copy() throws BatchException {
        return new TestJobEnhancements(this);
    }
}

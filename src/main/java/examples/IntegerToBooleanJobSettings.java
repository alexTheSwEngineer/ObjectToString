package examples;

import com.sun.org.apache.xpath.internal.operations.Bool;
import main.batchJob.JobEnhancements;
import main.batchJob.SimpleBatchAction;
import main.common.BatchException;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class IntegerToBooleanJobSettings extends JobEnhancements<Integer,Boolean,SimpleBatchAction<Integer, Boolean>,IntegerToBooleanJobSettings> {
    protected IntegerToBooleanJobSettings() throws BatchException {
        super();
    }

    protected IntegerToBooleanJobSettings(JobEnhancements<Integer, Boolean, SimpleBatchAction<Integer, Boolean>, IntegerToBooleanJobSettings> other) throws BatchException {
        super(other);
    }

    @Override
    public IntegerToBooleanJobSettings copy() throws BatchException {
        return new IntegerToBooleanJobSettings(this);
    }
}

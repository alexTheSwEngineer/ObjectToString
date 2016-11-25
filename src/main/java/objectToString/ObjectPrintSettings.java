package objectToString;

import main.batchJob.JobEnhancements;
import main.common.BatchException;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class ObjectPrintSettings<T> extends JobEnhancements<T,Property,ExtractPropertyAction<T>,ObjectPrintSettings<T>> {

    public ObjectPrintSettings() throws BatchException {
        super();
    }

    public ObjectPrintSettings(JobEnhancements<T, Property, ExtractPropertyAction<T>, ObjectPrintSettings<T>> other) throws BatchException {
        super(other);
    }

    @Override
    public ObjectPrintSettings<T> copy() throws BatchException {
            return new ObjectPrintSettings<T>(this);
    }
}

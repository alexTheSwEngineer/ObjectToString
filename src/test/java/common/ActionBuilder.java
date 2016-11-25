package common;

import main.batchJob.interfaces.IBatchAction;
import main.common.BatchException;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class ActionBuilder<Tin,Tout>{
    private boolean allows;
    private boolean breakBefore;
    private boolean breakAfter;
    private Tout returns;
    public ActionBuilder allowReturns(boolean b){
        this.allows=b;
        return this;
    }
    public ActionBuilder breakBeforeReturns(boolean b){
        this.breakBefore=b;
        return this;
    }
    public ActionBuilder breakAfterReturns(boolean b){
        this.breakAfter=b;
        return this;
    }
    public ActionBuilder resultIs(Tout b){
        this.returns=b;
        return this;
    }

    public IBatchAction<Tin,Tout> build(){
        return  new IBatchAction<Tin, Tout>() {
            @Override
            public boolean allow(Tin input) {
                return allows;
            }

            @Override
            public boolean breakBefore(Tin input) {
                return breakBefore;
            }

            @Override
            public boolean breakAfter(Tin input, Tout output) {
                return breakAfter;
            }

            @Override
            public Tout accept(Tin in) throws BatchException {
                return returns;
            }
        };
    }
}

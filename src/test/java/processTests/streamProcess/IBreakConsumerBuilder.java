package processTests.streamProcess;

import main.common.BatchException;
import main.processing.IBreakConsumer;

import java.util.function.Consumer;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class IBreakConsumerBuilder<Tin> {
    private boolean breakBefore;
    private boolean breakAfter;
    private boolean allow;
    private Consumer<Tin> act = x->{};

    public IBreakConsumerBuilder<Tin> breaksBefore(boolean b){
        breakBefore=b;
        return this;
    }
    public IBreakConsumerBuilder<Tin> breaksAfter(boolean b){
        breakAfter=b;
        return this;
    }
    public IBreakConsumerBuilder<Tin> allows(boolean b){
        allow=b;
        return this;
    }

    public IBreakConsumerBuilder<Tin> act(Consumer<Tin> a){
        this.act=a;
        return this;
    }

    public  IBreakConsumer<Tin> build(){
        return  new IBreakConsumer<Tin>() {
            @Override
            public boolean breakBeforeExec(Tin in) throws BatchException {
                return breakBefore;
            }

            @Override
            public boolean allowExec(Tin in) throws BatchException {
                return allow;
            }

            @Override
            public boolean breakAfterExecc(Tin tin) throws BatchException {
                return breakAfter;
            }

            @Override
            public void accept(Tin in) throws BatchException {
                act.accept(in);
                return;
            }
        };
    }
}

package processTests.streamProcess;

import main.common.BatchException;
import main.processing.ActionProcess;
import main.processing.IBreakConsumer;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by atrposki on 25-Nov-16.
 */
public class StreamProcessTestCase {
    private List<IBreakConsumer<StringBuilder>> actions = new LinkedList<IBreakConsumer<StringBuilder>>();
    private String expects;
    private String desc;
    public String toString(){
        return this.desc;
    }
    public  StreamProcessTestCase descIs(String s){
        this.desc=s;
        return this;
    }
    public StreamProcessTestCase addAction(IBreakConsumer<StringBuilder> a){
        actions.add(a);
        return this;
    }

    public ActionProcess<StringBuilder> buildSut(boolean propagates) throws BatchException {
        ActionProcess<StringBuilder> sut = new ActionProcess<StringBuilder>(propagates);
        for (IBreakConsumer<StringBuilder> action :
                actions) {
            sut.add(action);
        }
        return sut;
    }

    public StreamProcessTestCase expects(String str){
        expects=str;
        return this;
    }

    public String getExpected() {
        return expects;
    }
}

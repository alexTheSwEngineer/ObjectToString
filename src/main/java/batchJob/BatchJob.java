package batchJob;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by atrposki on 11/15/2016.
 */
public class BatchJob<TSource, TSourceTransform,TParam> {
    private List<Function<TSource, TSourceTransform>> transforms;
    private BiConsumer<TSourceTransform,TParam> beforeFirst;
    private BiConsumer<TSourceTransform,TParam> beforeEach;
    private BiConsumer<TSourceTransform,TParam> afterEach;
    private BiConsumer<TSourceTransform,TParam> applyToEach;
    private TriConsumer<TSourceTransform, TSourceTransform,TParam> between;
    private BiConsumer<TSourceTransform,TParam> last;

    public BatchJob(List<Function<TSource, TSourceTransform>> actions){
        this.transforms = actions;
        beforeFirst = BatchJob::NOP;
        applyToEach = BatchJob::NOP;
        between= BatchJob::NOP;
        beforeEach =BatchJob::NOP;
        afterEach = BatchJob::NOP;
        last = BatchJob::NOP;
    }

    public BatchJob(BatchJob<TSource, TSourceTransform,TParam> other){
        this(other.transforms);
        this.beforeFirst=other.beforeFirst;
        this.applyToEach=other.applyToEach;
        this.between=other.between;
        this.beforeEach=other.beforeEach;
        this.afterEach=other.afterEach;
        this.last=other.last;
    }


    public BatchJob<TSource, TSourceTransform,TParam> beforeFirst(BiConsumer<TSourceTransform,TParam> before)throws Exception {
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.beforeFirst = BatchJob.DefaultIfNull(before);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> beforeEach(BiConsumer<TSourceTransform,TParam> before)throws Exception {
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.beforeEach = BatchJob.DefaultIfNull(before);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> afterEach(BiConsumer<TSourceTransform,TParam> before)throws Exception {
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.afterEach = BatchJob.DefaultIfNull(before);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> applyOnEach(BiConsumer<TSourceTransform,TParam> print) throws Exception{
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.applyToEach = BatchJob.DefaultIfNull(print);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> betweenEach(TriConsumer<TSourceTransform,TSourceTransform,TParam> between) throws Exception{
        if(between==null){
            this.between= BatchJob::NOP;
        }else {
            this.between = between;
        }
        return this;
    }

    public BatchJob<TSource, TSourceTransform,TParam> afterLast(BiConsumer<TSourceTransform,TParam> after) throws Exception{
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.last = BatchJob.DefaultIfNull(after);
        return  res;
    }

    public Stream<TSourceTransform> execute(TSource obj, TParam parameter) throws Exception {
        List<TSourceTransform> processed = new ArrayList<TSourceTransform>();
        boolean isFirst = true;
        Function<TSource, TSourceTransform> previousAction = null;
        TSourceTransform previousResult = null;
        for (Function<TSource, TSourceTransform> action: transforms) {
            TSourceTransform result = action.apply(obj);
            if(isFirst){
                beforeFirst.accept(result,parameter);
                isFirst=false;
            }

            if(previousAction!=null){
                between.apply(previousResult,result,parameter);
            }

            beforeEach.accept(result,parameter);
            applyToEach.accept(result,parameter);
            afterEach.accept(result,parameter);

            previousAction=action;
            previousResult = result;
            processed.add(result);
        }

        if(!transforms.isEmpty()){
            last.accept(previousResult,parameter);
        }

        return processed.stream();
    }

    private static <Q,P> void NOP(Q property,Q p2, P p3){}

    private static <Q> void NOP(Q l, Q r){}

    private  static <Q,P> BiConsumer<Q,P> DefaultIfNull(BiConsumer<Q,P> consumer){
        if(consumer!=null){
            return consumer;
        }
        return BatchJob::NOP;
    }
}

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
    private List<Function<TSource, TSourceTransform>> actions;
    private BiConsumer<TSourceTransform,TParam> first;
    private BiConsumer<TSourceTransform,TParam> beforeAll;
    private BiConsumer<TSourceTransform,TParam> afterAll;
    private BiConsumer<TSourceTransform,TParam> apply;
    private TriConsumer<TSourceTransform, TSourceTransform,TParam> between;
    private BiConsumer<TSourceTransform,TParam> last;

    public BatchJob(List<Function<TSource, TSourceTransform>> actions){
        this.actions = actions;
        first = BatchJob::NOP;
        apply = BatchJob::NOP;
        between= BatchJob::NOP;
        beforeAll=BatchJob::NOP;
        afterAll = BatchJob::NOP;
        last = BatchJob::NOP;
    }

    public BatchJob(BatchJob<TSource, TSourceTransform,TParam> other){
        this(other.actions);
        this.first =other.first;
        this.apply =other.apply;
        this.between=other.between;
        this.last =other.last;
    }


    public BatchJob<TSource, TSourceTransform,TParam> first(BiConsumer<TSourceTransform,TParam> before)throws Exception {
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.first = BatchJob.DefaultIfNull(before);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> beforeEach(BiConsumer<TSourceTransform,TParam> before)throws Exception {
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.beforeAll= BatchJob.DefaultIfNull(before);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> afterEach(BiConsumer<TSourceTransform,TParam> before)throws Exception {
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.afterAll= BatchJob.DefaultIfNull(before);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> applyOnEach(BiConsumer<TSourceTransform,TParam> print) throws Exception{
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.apply = BatchJob.DefaultIfNull(print);
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

    public BatchJob<TSource, TSourceTransform,TParam> last(BiConsumer<TSourceTransform,TParam> after) throws Exception{
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.last = BatchJob.DefaultIfNull(after);
        return  res;
    }

    public Stream<TSourceTransform> execute(TSource obj, TParam parameter) throws Exception {
        List<TSourceTransform> processed = new ArrayList<TSourceTransform>();
        boolean isFirst = true;
        Function<TSource, TSourceTransform> previousAction = null;
        TSourceTransform previousResult = null;
        for (Function<TSource, TSourceTransform> action:actions) {
            TSourceTransform result = action.apply(obj);
            if(isFirst){
                first.accept(result,parameter);
            }

            if(previousAction!=null){
                between.apply(previousResult,result,parameter);
            }

            beforeAll.accept(result,parameter);
            apply.accept(result,parameter);
            afterAll.accept(result,parameter);

            previousAction=action;
            previousResult = result;
            processed.add(result);
        }

        if(!actions.isEmpty()){
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

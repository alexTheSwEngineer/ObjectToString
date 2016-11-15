package batchJob;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Created by atrposki on 11/15/2016.
 */
public class BatchJob<TSource, TSourceTransform,TParam> {
    private List<Function<TSource, TSourceTransform>> actions;
    private BiConsumer<TSourceTransform,TParam> before;
    private BiConsumer<TSourceTransform,TParam> apply;
    private TriConsumer<TSourceTransform, TSourceTransform,TParam> between;
    private BiConsumer<TSourceTransform,TParam> after;

    public BatchJob(List<Function<TSource, TSourceTransform>> actions){
        this.actions = actions;
        before= BatchJob::NOP;
        apply = BatchJob::NOP;
        between= BatchJob::NOP;
        after= BatchJob::NOP;
    }

    public BatchJob(BatchJob<TSource, TSourceTransform,TParam> other){
        this(other.actions);
        this.before=other.before;
        this.apply =other.apply;
        this.between=other.between;
        this.after=other.after;
    }


    public BatchJob<TSource, TSourceTransform,TParam> beforeAll(BiConsumer<TSourceTransform,TParam> before)throws Exception {
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.before= BatchJob.DefaultIfNull(before);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> applyOnEach(BiConsumer<TSourceTransform,TParam> print) throws Exception{
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.apply = BatchJob.DefaultIfNull(print);
        return  res;
    }

    public BatchJob<TSource, TSourceTransform,TParam> between(TriConsumer<TSourceTransform,TSourceTransform,TParam> between) throws Exception{
        if(between==null){
            this.between= BatchJob::NOP;
        }else {
            this.between = between;
        }
        return this;
    }

    public BatchJob<TSource, TSourceTransform,TParam> afterAll(BiConsumer<TSourceTransform,TParam> after) throws Exception{
        BatchJob<TSource, TSourceTransform,TParam> res = new BatchJob<>(this);
        res.after= BatchJob.DefaultIfNull(after);
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
                before.accept(result,parameter);
            }

            if(previousAction!=null){
                between.apply(previousResult,result,parameter);
            }

            apply.accept(result,parameter);

            previousAction=action;
            previousResult = result;
            processed.add(result);
        }

        if(!actions.isEmpty()){
            after.accept(previousResult,parameter);
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

package batchJob;

import examples.VeryComplexObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by atrposki on 11/14/2016.
 */
public class BatchSettings<TSource, TActionParam> {
    private List<Function<TSource,TActionParam>> actions;

    public BatchSettings(Collection<Function<TSource,TActionParam>> actions){
        this.actions=actions.stream().collect(Collectors.toList());
    }

    public BatchSettings() {
        this.actions = new ArrayList<>();
    }

    public BatchSettings(BatchSettings<TSource, TActionParam> other) {
        this.actions = other.actions
                .stream()
                .collect(Collectors.toList());
    }

    public <TSetup> TSetup SetUp(Function<BatchSettings<TSource,TActionParam>,TSetup> setupFactory){
        return setupFactory.apply(this);
    }

    public BatchSettings<TSource, TActionParam> add(Function<TSource, TActionParam> action) {
        actions.add(action);
        return this;
    }

    public <TParam> BatchJob<TSource, TActionParam,TParam> createJob() {
        return new BatchJob<TSource, TActionParam,TParam>(this.actions);
    }

    public <TParam> BatchJob<TSource, TActionParam,TParam> createJob(Function<BatchSettings<TSource,TActionParam>,BatchJob<TSource,TActionParam,TParam>> factory) {
        return factory.apply(this);
    }

    public List<TActionParam> apply(TSource obj) {
        return actions.stream().map(x -> x.apply(obj)).collect(Collectors.toList());
    }
}

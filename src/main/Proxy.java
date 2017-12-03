package main;

import main.requests.MethodConsume;
import main.requests.MethodProduce;
import main.requests.MethodRequest;

import java.util.List;

public class Proxy<T> {
    private Scheduler scheduler;
    private Buffer<T> buffer;

    public Proxy(int bufferSize){
        this.scheduler = new Scheduler();
        this.buffer = new Buffer<>(bufferSize);
        this.scheduler.startScheduler();
    }

    public Future<Boolean> produce(List<T> values){
        Future<Boolean> future = new Future<>();
        MethodRequest methodRequest = new MethodProduce<>(this.buffer, future, values);
        this.scheduler.enqueue(methodRequest);
        return future;
    }

    public Future<List<T>> consume(int quantity){
        Future<List<T>> future = new Future<>();
        MethodRequest methodRequest = new MethodConsume<>(this.buffer, future, quantity);
        this.scheduler.enqueue(methodRequest);
        return future;
    }

}

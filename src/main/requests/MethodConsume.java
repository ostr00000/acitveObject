package main.requests;

import main.Buffer;
import main.Future;

import java.util.List;

public class MethodConsume<T> implements MethodRequest {
    private Buffer<T> buffer;
    private Future<List<T>> future;
    private int quantity;

    public MethodConsume(Buffer<T> buffer, Future<List<T>> future, int quantity) {
        this.buffer = buffer;
        this.future = future;
        this.quantity = quantity;
    }

    @Override
    public boolean guard() {
        return this.buffer.isAssignedSpace(quantity);
    }

    @Override
    public void call() {
        this.future.setResult(this.buffer.remove(quantity));
    }
}

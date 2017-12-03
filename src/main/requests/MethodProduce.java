package main.requests;

import main.Buffer;
import main.Future;

import java.util.List;

public class MethodProduce<T> implements MethodRequest {
    private Buffer<T> buffer;
    private Future<Boolean> future;
    private List<T> values;

    public MethodProduce(Buffer<T> buffer, Future<Boolean> future, List<T> values) {
        this.buffer = buffer;
        this.future = future;
        this.values = values;
    }

    @Override
    public boolean guard() {
        return buffer.isFreeSpace(this.values.size());
    }

    @Override
    public void call() {
        this.buffer.add(values);
        this.future.setResult(Boolean.TRUE);

    }
}

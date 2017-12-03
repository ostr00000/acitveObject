package main.requests;

public interface MethodRequest {
    boolean guard();
    void call();
}

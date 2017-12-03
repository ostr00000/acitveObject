package main.activationlist;

import main.requests.MethodRequest;

public interface ActivationList {
    void enqueue(MethodRequest methodRequest);
    MethodRequest dequeue() throws InterruptedException;
}

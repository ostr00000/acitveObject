package main;

import main.activationlist.ActivationList;
import main.activationlist.ActivationListTwoQueue;
import main.requests.MethodRequest;

public class Scheduler implements Runnable {
    private ActivationList activationList = new ActivationListTwoQueue();

    private void dispatch() throws InterruptedException {
        MethodRequest methodRequest = this.activationList.dequeue();
        methodRequest.call();
    }

    void startScheduler(){
        Thread thread = new Thread(this, "scheduler");
        thread.setDaemon(true);
        thread.start();
    }

    void enqueue(MethodRequest methodRequest) {
        this.activationList.enqueue(methodRequest);
    }

    @Override
    public void run() {
        try {
            while (true) {
                dispatch();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

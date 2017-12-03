package main.activationlist;

import main.requests.MethodConsume;
import main.requests.MethodProduce;
import main.requests.MethodRequest;

import java.util.LinkedList;
import java.util.Queue;

public class ActivationListTwoQueue implements ActivationList {
    private Queue<MethodConsume> consumeQueue = new LinkedList<>();
    private Queue<MethodProduce> produceQueue = new LinkedList<>();
    private MethodProduce selectedMethodProduce = null;
    private MethodConsume selectedMethodConsume = null;
    private boolean lastTimeConsumerQueue = true;

    @Override
    public String toString() {
        return "ActivationListTwoQueue{" +
                "consumeQueue=" + consumeQueue +
                ", produceQueue=" + produceQueue +
                ", selectedMethodProduce=" + selectedMethodProduce +
                ", selectedMethodConsume=" + selectedMethodConsume +
                ", lastTimeConsumerQueue=" + lastTimeConsumerQueue +
                "}\n";
    }

    @Override
    public synchronized void enqueue(MethodRequest methodRequest) {
        if (methodRequest instanceof MethodConsume) {
            MethodConsume methodConsume = (MethodConsume) methodRequest;
            this.consumeQueue.add(methodConsume);
        } else if (methodRequest instanceof MethodProduce) {
            MethodProduce methodProduce = (MethodProduce) methodRequest;
            this.produceQueue.add(methodProduce);
        } else {
            throw new IllegalArgumentException("unknown MethodRequest");
        }
        notify();
    }

    @Override
    public synchronized MethodRequest dequeue() throws InterruptedException {
        MethodRequest ret;
        while (true) {

            ret = chooseMethod();
            if (ret != null && ret.guard()) {
                remove(ret);
                return ret;
            }
            wait();
        }
    }

    private void remove(MethodRequest ret) {
        if(ret instanceof MethodConsume){
            this.selectedMethodConsume = null;
        }else if(ret instanceof  MethodProduce) {
            this.selectedMethodProduce = null;
        }else {
            throw new IllegalArgumentException("unknown MethodRequest");
        }
    }

    private MethodRequest chooseMethod(){
        if (lastTimeConsumerQueue) {
            lastTimeConsumerQueue = false;
            if(checkProducer()) return this.selectedMethodProduce;
            if(checkConsumer()) return this.selectedMethodConsume;
        } else {
            lastTimeConsumerQueue = true;
            if(checkConsumer()) return this.selectedMethodConsume;
            if(checkProducer()) return this.selectedMethodProduce;
        }
        return null;
    }



    private boolean checkProducer() {
        if (this.selectedMethodProduce != null){
            return true;
        }
        if(!this.produceQueue.isEmpty()) {
            this.selectedMethodProduce = this.produceQueue.remove();
            return true;
        }
        return false;
    }

    private boolean checkConsumer() {
        if(this.selectedMethodConsume != null){
            return true;
        }
        if (!this.consumeQueue.isEmpty()) {
            this.selectedMethodConsume = this.consumeQueue.remove();
            return true;
        }
        return false;
    }

}

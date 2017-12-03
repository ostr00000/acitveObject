package main;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    
    public static void main(String[] args) {

        int bufferSize = 40;
        Proxy<Integer> proxy = new Proxy<>(bufferSize);

        List<Future> future = new ArrayList<>();

        future.add(proxy.consume(10));
        future.add(proxy.produce(Collections.nCopies(30, 1)));
        future.add(proxy.produce(Collections.nCopies(7,2)));
        future.add(proxy.consume(15));
        future.add(proxy.consume(10));
        future.add(proxy.produce(Collections.nCopies(5,3)));

        for(Future f : future){
            while (!f.isAvaliable());
            System.out.println(f.getResult());
        }

    }
}

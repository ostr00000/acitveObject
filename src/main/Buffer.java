package main;

import java.util.*;

public class Buffer<T> {
    private Deque<T> fields;
    private int size;
    private int freeSpace;

    @Override
    public String toString() {
        return "Buffer{" +
                "fields=" + fields +
                ", size=" + size +
                ", freeSpace=" + freeSpace +
                '}';
    }

    public Buffer(int size) {
        this.size = size;
        this.freeSpace = size;
        this.fields = new ArrayDeque<>(size);
        System.err.println(this);
    }

    public void add(List<T> values) {
        if (this.freeSpace - values.size() < 0) {
            throw new IllegalArgumentException("too many items");
        }
        for (T val : values) {
            this.fields.addFirst(val);
        }
        this.freeSpace -= values.size();
        System.err.println(this);
    }

    public List<T> remove(int quantity) {
        if (this.size - this.freeSpace < quantity) {
            throw new IllegalArgumentException("not enough items");
        }
        this.freeSpace += quantity;
        List<T> result = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            result.add(this.fields.removeLast());
        }
        System.err.println(this);
        return result;
    }

    public boolean isFreeSpace(int quantity) {
        return (this.freeSpace - quantity) >= 0;
    }

    public boolean isAssignedSpace(int quantity) {
        return (this.size - this.freeSpace) >= quantity;
    }

}

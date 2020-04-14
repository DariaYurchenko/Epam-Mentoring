package com.epam.exercises.circularbuffer;

import com.epam.exercises.circularbuffer.exceptions.IllegalOperationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CircularBuffer<T> {
    private int head;
    private int tail;
    private int size;
    private T[] items;

    public CircularBuffer(int capacity) {
        this.items = (T[]) new Object[capacity];
    }

    public void put(T item) throws IllegalOperationException {
        if(isFull()) {
            throw new IllegalOperationException("You are trying to put element to the full list.");
        }
        items[head++] = item;
        head %= items.length;
        size++;
    }

    public T get() throws IllegalOperationException {
        if(isEmpty()) {
            throw new IllegalOperationException("You are trying to get element from the empty list.");
        }
        tail %= items.length;
        size--;
        return items[tail++];
    }

    public Object[] toObjectArray() {
        Object[] objects = new Object[size];
        for(int i = 0, k = tail; i < size; i++, k %= items.length) {
            objects[i] = items[k++];
        }
        return objects;
    }

    public T[] toArray() {
        T[] objects = (T[]) new Object[size];
        for(int i = 0, k = tail; i < size; i++, k %= items.length) {
            objects[i] = items[k++];
        }
        return objects;
    }

    public List<T> asList() {
        List<T> list = new ArrayList<>();
        for(int i = 0, k = tail; i < size; i++, k %= items.length) {
            list.add(i, items[k++]);
        }
        return list;
    }

    public void addAll(List<? extends T> toAdd) throws IllegalOperationException {
        if((items.length - size) < toAdd.size()) {
            throw new IllegalOperationException("You are trying to put too much elements.");
        }
        toAdd.forEach(this::put);
    }

    public void sort(Comparator<? super T> comparator) {
        T[] t = toArray();
        Arrays.sort(t, comparator);
        for(int i = 0, k = tail; i < size; i++, k %= items.length) {
            items[k++] = t[i];
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean isFull() {
        return tail == head && !isEmpty();
    }

    public static void main(String[] args) {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(5);

        System.out.println(buffer.isEmpty());

        buffer.put(8);
        buffer.put(1);
        buffer.put(2);
        buffer.put(6);
        buffer.put(5);
        buffer.get();
        buffer.get();

        System.out.println(buffer.isEmpty());

        System.out.println(buffer.asList());
        System.out.println(Arrays.toString(buffer.toObjectArray()));
        System.out.println(Arrays.toString(buffer.toArray()));

        List<Integer> list = new ArrayList<>();
        list.add(11);
        buffer.addAll(list);

        System.out.println(Arrays.toString(buffer.toArray()));

        buffer.sort(Integer::compareTo);
        System.out.println(Arrays.toString(buffer.toArray()));

    }

}

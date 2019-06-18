package br.edu.ifpb.sdatvmulthreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BufferBlocking implements Buffer {

    private final BlockingQueue<Integer> queue;

    public BufferBlocking() {
        this.queue = new ArrayBlockingQueue<>(3);
    }

    @Override
    public void set(int value) {
        try {
            queue.put(value);
            System.out.println("Pordutor grava: " + value);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int get() {
        int readedValue = 0;

        try {
            readedValue = queue.take();
            System.out.println("Consumidor ler: " + readedValue);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        return readedValue;
    }
}

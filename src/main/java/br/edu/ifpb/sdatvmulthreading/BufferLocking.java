package br.edu.ifpb.sdatvmulthreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

    public class BufferLocking implements Buffer {

    private Lock lock = new ReentrantLock(false);
    private Condition canRead = lock.newCondition();
    private Condition canWrite = lock.newCondition();

    private Integer buffer = -1;
    private Boolean empty = true;

    @Override
    public void set(int value) {
        lock.lock();

        try {
            while (!empty) {
                System.out.println("Produtor tenta gravar");
                canWrite.await();
            }
            buffer = value;
            System.out.println("Produto grava " + buffer);
            empty = false;
            canRead.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public int get() {
        lock.lock();

        try {
            while (empty) {
                System.out.println("Consumidor tenta consumir mas o bufer está vazio");
                canRead.await();
            }
            empty = true;
            System.out.println("Consumidor ler: " + buffer);
            canWrite.signalAll();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return buffer;
    }
}

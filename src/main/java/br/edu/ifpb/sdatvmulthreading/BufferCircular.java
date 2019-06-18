package br.edu.ifpb.sdatvmulthreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferCircular implements Buffer {

    private Lock lock = new ReentrantLock(false);
    private Condition canRead = lock.newCondition();
    private Condition canWrite = lock.newCondition();

    private Integer[] buffer = {-1, -1, -1};
    private Integer usedBuffers = 0;
    private Integer readIndex = 0;
    private Integer writeIndex = 0;

    @Override
    public void set(int value) {

        try {
            lock.lock();

            if (usedBuffers.equals(buffer.length)) {
                System.out.println("Produtor tenta gravar mas o buffer está cheio. Em espera");
                canWrite.await();
            }

            buffer[writeIndex] = value;
            System.out.println("Produtor grava " + value + " posição " + writeIndex);
            if (writeIndex == (buffer.length - 1)) writeIndex = -1;

            writeIndex++;
            usedBuffers++;

            canRead.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    @Override
    public int get() {
        Integer readedValue = 0;

        lock.lock();

        try {
            if (usedBuffers.equals(0)) {
                System.out.println("Consumidor tenta ler mas o buffer esta vazio. Em espera");
                canRead.await();
            }

            readedValue = buffer[readIndex];
            System.out.println("Consumidor ler " + readedValue + " posição " + readIndex);
            if (readIndex == (buffer.length -1)) readIndex = -1;

            readIndex++;
            usedBuffers--;

            canWrite.signal();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

        return readedValue;
    }
}

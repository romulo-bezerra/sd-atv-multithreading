package br.edu.ifpb.sdatvmulthreading;

import java.util.Random;

public class Consumidor implements Runnable {

    private final Buffer buffer;
    private final Random random = new Random();

    public Consumidor(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int soma = 0;
        for (int i = 0; i <= 10; i++ ) {
            try {
                Thread.sleep(random.nextInt(3000));
                soma += buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("\n%s%d\n", "Fim do Consumidor. Valor da soma: ", soma);
    }
}

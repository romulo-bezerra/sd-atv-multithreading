package br.edu.ifpb.sdatvmulthreading;

public class BufferExemplo implements Buffer {

    private Integer buffer = -1;


    @Override
    public void set(int value) {
        System.out.printf("Produtor grava: \t%2d\n", value);
        this.buffer = value;
    }

    @Override
    public int get() {
        System.out.printf("Consumidor lê: \t%2d\n", buffer.toString());
        return buffer;
    }

}

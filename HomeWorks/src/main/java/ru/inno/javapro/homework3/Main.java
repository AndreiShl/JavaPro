package ru.inno.javapro.homework3;

public class Main {

    public static void main(String[] args) {
        Runnable runnable = () -> {
            int delay = (int) (Math.random() * 1000);
            System.out.println("==> " + Thread.currentThread().getName() + ", delay: " + delay);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("<== " + Thread.currentThread().getName() + " finish job");
        };
        MyThreadPool pool = new MyThreadPool(4);
        for (int i = 0; i < 20; i++) {
            pool.execute(runnable);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        pool.shutdown();
        System.out.println("pool shutdown");
        pool.execute(runnable); // получаем исключение IllegalStateException
    }
}

package ru.inno.javapro.homework3;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Попробуйте реализовать собственный пул потоков.
 * В качестве аргументов конструктора пулу передается его емкость (количество рабочих потоков).
 * Как только пул создан, он сразу инициализирует и запускает потоки.
 * Внутри пула очередь задач на исполнение организуется через LinkedList<Runnable>.
 * При выполнении у пула потоков метода execute(Runnabler),
 * указанная задача должна попасть в очередь исполнения,
 * и как только появится свободный поток – должна быть выполнена.
 * Также необходимо реализовать метод shutdown(), после выполнения
 * которого новые задачи больше не принимаются пулом
 * (при попытке добавить задачу можно бросать IllegalStateException),
 * и все потоки для которых больше нет задач завершают свою работу.
 * Дополнительно можно добавить метод awaitTermination() без таймаута,
 * работающий аналогично стандартным пулам потоков
 */
public class MyThreadPool {
    private final Thread[] threads;
    private final LinkedList<Runnable> tasks;
    private AtomicBoolean running;
    private final Object monitorPoll = new Object();
    private final Object monitorAdd = new Object();

    public MyThreadPool(int numThreads) {
        threads = new Thread[numThreads];
        tasks = new LinkedList<>();
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread(new worker());
            threads[i].start();
        }
        running = new AtomicBoolean(true);
    }

    public void execute(Runnable task) {
        if (running.get()) {
            synchronized (monitorAdd) {
                tasks.addLast(task);
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public void shutdown() {
        running.set(false);
    }

    private final class worker implements Runnable {
        @Override
        public void run() {
            while (running.get()) {
                Runnable nextTask;
                synchronized (monitorPoll) {
                    nextTask = tasks.pollFirst();
                }
                if (nextTask != null) {
                    nextTask.run();
                }

            }
        }
    }
}

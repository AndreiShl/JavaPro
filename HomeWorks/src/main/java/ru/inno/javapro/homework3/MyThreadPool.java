package ru.inno.javapro.homework3;

import java.util.LinkedList;
import java.util.concurrent.Executor;

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
public class MyThreadPool implements Executor {
    private final LinkedList<Runnable> tasks;
    private volatile boolean running = true;
    private final Object monitor = new Object();

    public MyThreadPool(int numThreads) {
        tasks = new LinkedList<>();
        for (int i = 0; i < numThreads; i++) {
            new Thread(new worker()).start();
        }
    }

    public void execute(Runnable task) {
        if (running) {
            tasks.addLast(task);
        } else {
            throw new IllegalStateException();
        }
    }

    public void shutdown() {
        running = false;
    }

    private final class worker implements Runnable {
        @Override
        public void run() {
            while (running) {
                Runnable nextTask;
                synchronized (monitor) {
                    nextTask = tasks.pollFirst();
                }
                if (nextTask != null) {
                    nextTask.run();
                }

            }
        }
    }
}

package bbb;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Salmon
 * @since 2024-07-18
 */
public class MainTest {
    public static void main(String[] args) {
        System.out.println("hello salmon's jclasslib");
        // 创建一个 bbb.Person 对象
        Person person = new Person("Alice", 30);

        // 使用自动生成的访问器方法获取字段值
        System.out.println("Name: " + person.name()); // 输出: Name: Alice
        System.out.println("Age: " + person.age());   // 输出: Age: 30

        // 使用自动生成的 toString() 方法
        System.out.println(person); // 输出: bbb.Person[name=Alice, age=30]

        // 比较两个 bbb.Person 对象
        Person person2 = new Person("Alice", 30);
        System.out.println(person.equals(person2)); // 输出: true

        // 输出 hashCode 值
        System.out.println(person.hashCode());
    }

}

record Person(String name, int age) {
}

class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            System.out.println("你是谁呀");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
                System.out.println("我是Salmon");
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t1.start();
        t2.start();
        System.out.println("我等你。。。");
        t2.join();
        System.out.println("我是啦啦啦");
    }
}


class Resource1 {
}

class Resource2 {
}

class Thread1 extends Thread {
    private final Resource1 resource1;
    private final Resource2 resource2;

    public Thread1(Resource1 r1, Resource2 r2) {
        this.resource1 = r1;
        this.resource2 = r2;
    }

    @Override
    public void run() {
        synchronized (resource1) {
            System.out.println("bbb.Thread1: locked bbb.Resource1");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            synchronized (resource2) {
                System.out.println("bbb.Thread1: locked bbb.Resource2");
            }
        }
    }
}

class Thread2 extends Thread {
    private final Resource1 resource1;
    private final Resource2 resource2;

    public Thread2(Resource1 r1, Resource2 r2) {
        this.resource1 = r1;
        this.resource2 = r2;
    }

    @Override
    public void run() {
        synchronized (resource2) {
            System.out.println("bbb.Thread2: locked bbb.Resource2");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }

            synchronized (resource1) {
                System.out.println("bbb.Thread2: locked bbb.Resource1");
            }
        }
    }
}

/**
 * 死锁模拟
 */
class DeadlockExample {
    public static void main(String[] args) {
        Resource1 r1 = new Resource1();
        Resource2 r2 = new Resource2();

        Thread1 t1 = new Thread1(r1, r2);
        Thread2 t2 = new Thread2(r1, r2);

        t1.start();
        t2.start();
    }
}


class RunnableExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(1);

        Runnable task1 = () -> {
            System.out.println("Executing Task 1");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Runnable task2 = () -> {
            System.out.println("Executing Task 2");
        };

        executor.submit(task1);
        executor.submit(task2);

        executor.shutdown();
    }
}


class CallableExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Callable<String> task1 = () -> {
            return "Result of Task 1";
        };

        Callable<String> task2 = () -> {
            return "Result of Task 2";
        };

        Future<String> future1 = executor.submit(task1);
        Future<String> future2 = executor.submit(task2);

        try {
            System.out.println(future1.get());
            System.out.println(future2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();
    }
}

/**
 * 测试shutdown
 */
class ShutdownExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("Task completed");
            } catch (InterruptedException e) {
                System.out.println("Task interrupted");
            }
        };

        executor.submit(task);
        executor.submit(task);

        executor.shutdown();
        System.out.println("Executor shut down");

        while (!executor.isTerminated()) {
            // Wait for all tasks to complete
        }

        System.out.println("All tasks completed");
    }
}


/**
 * 测试shutdownNow
 */
class ShutdownNowExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("Task completed");
            } catch (InterruptedException e) {
                System.out.println("Task interrupted");
            }
        };

        executor.submit(task);
        executor.submit(task);

        List<Runnable> notExecutedTasks = executor.shutdownNow();
        System.out.println("Executor shut down immediately");

        System.out.println("Number of tasks not executed: " + notExecutedTasks.size());

        while (!executor.isTerminated()) {
            // Wait for all tasks to complete
        }

        System.out.println("All tasks completed or interrupted");
    }
}

class IsShutdownIsTerminatedExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable task = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("Task completed");
            } catch (InterruptedException e) {
                System.out.println("Task interrupted");
            }
        };

        executor.submit(task);
        executor.submit(task);

        executor.shutdown();

        System.out.println("Is executor shutdown? " + executor.isShutdown());

        while (!executor.isTerminated()) {
            // Wait for all tasks to complete
        }

        System.out.println("Is executor terminated? " + executor.isTerminated());
        System.out.println("All tasks completed");
    }
}

class SleepExample {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("bbb.Thread1 is going to sleep.");
            try {
                Thread.sleep(2000); // Sleep for 2 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("bbb.Thread1 woke up.");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("bbb.Thread2 is running.");
            // This thread does not interact with thread1 directly
        });

        thread1.start();
        thread2.start();
    }
}

class WaitExample {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("bbb.Thread1 acquired the lock and is waiting.");
                try {
                    lock.wait(2000); // Wait for 2 seconds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("bbb.Thread1 resumed after wait.");
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock) {
                System.out.println("bbb.Thread2 acquired the lock and will notify.");
                lock.notify(); // Notify waiting threads
            }
        });

        thread1.start();
        thread2.start();
    }
}


class NotifyExample {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Runnable task = () -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " acquired lock and is waiting.");
                try {
                    lock.wait(); // Wait until notified
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " resumed after being notified.");
//                lock.notify();
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        Thread thread3 = new Thread(task, "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();

        // Ensure that all threads are waiting before sending the notification
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (lock) {
            System.out.println("Main thread notifying one thread.");
            lock.notify(); // Notify only one waiting thread
        }
    }
}

class NotifyAllExample {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        Runnable task = () -> {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " acquired lock and is waiting.");
                try {
                    lock.wait(); // Wait until notified
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " resumed after being notified.");
//                lock.notify();
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");
        Thread thread3 = new Thread(task, "Thread-3");

        thread1.start();
        thread2.start();
        thread3.start();

        // Ensure that all threads are waiting before sending the notification
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (lock) {
            System.out.println("Main thread notifying all threads.");
            lock.notifyAll(); // Notify all waiting threads
        }
    }
}


/**
 * 保证可见性
 */
class VolatileVisibilityExample {
    private static volatile boolean flag = false;

    public static void main(String[] args) {
        Thread writer = new Thread(() -> {
            System.out.println("Writer thread starts.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            flag = true; // 修改 volatile 变量
            System.out.println("Writer thread set flag to true.");
        });

        Thread reader = new Thread(() -> {
            while (!flag) {
                // 等待 flag 变为 true
            }
            System.out.println("Reader thread detected flag is true.");
        });

        reader.start();
        writer.start();
    }
}

/**
 * 防止指令重排
 */
class VolatileReorderingExample {
    private static volatile boolean ready = false;
    private static int number = 0;

    public static void main(String[] args) {
        Thread reader = new Thread(() -> {
            while (!ready) {
                // 等待 ready 为 true
            }
            System.out.println("Number: " + number);
        });

        Thread writer = new Thread(() -> {
            number = 42; // 修改普通变量
            ready = true; // 修改 volatile 变量
        });

        reader.start();
        writer.start();
    }
}

class ReorderingExample {
    private static volatile boolean flag = false;
    private static int number = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread reader = new Thread(() -> {
            int localNumber = 0;
            while (!flag) {
                // 等待 flag 为 true
            }
            localNumber = number;
            System.out.println("Number: " + localNumber);
        });

        Thread writer = new Thread(() -> {
            number = 42; // 修改普通变量
            flag = true; // 修改普通变量
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            System.out.println(number);
            number = 35;
            System.out.println(number);
            number = 56;
        });
        reader.start();
        writer.start();
//        Thread.sleep(5000);
    }
}

class DemoRun implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "我抢锁");
        synchronized (StopDemp.lock) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (StopDemp.lock) {
                System.out.println(Thread.currentThread().getName() + "再次获取锁...");
            }
            System.out.println(Thread.currentThread().getName() + "我自杀");
//            Thread.interrupted();
        }
    }
}

class StopDemp {
    public static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        DemoRun demoRun = new DemoRun();
        Thread t1 = new Thread(demoRun);
        Thread t2 = new Thread(demoRun);
        t1.start();
        t2.start();
        Thread.sleep(1000);
//        t1.stop();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


class ABASolution {
    // 初始值和初始标记（版本）
    private static final AtomicStampedReference<Integer> atomicStampedReference =
            new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            int[] stampHolder = new int[1];
            Integer value = atomicStampedReference.get(stampHolder);
            System.out.println("Thread 1 - initial value: " + value + ", initial stamp: " + stampHolder[0]);

            // 尝试将值从 100 修改为 101，再修改回 100
            boolean success = atomicStampedReference.compareAndSet(100, 101, stampHolder[0], stampHolder[0] + 1);
            System.out.println("Thread 1 - change value to 101: " + success);

            value = atomicStampedReference.get(stampHolder);
            success = atomicStampedReference.compareAndSet(101, 100, stampHolder[0], stampHolder[0] + 1);
            System.out.println("Thread 1 - change value back to 100: " + success);

            value = atomicStampedReference.get(stampHolder);
            System.out.println("Thread 1 - final value: " + value + ", final stamp: " + stampHolder[0]);
        });

        Thread t2 = new Thread(() -> {
//            try {
//                // 保证 t1 线程先执行
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            int[] stampHolder = new int[1];
            Integer value = atomicStampedReference.get(stampHolder);
            System.out.println("Thread 2 - initial value: " + value + ", initial stamp: " + stampHolder[0]);

            // 尝试将值从 100 修改为 101
            boolean success = atomicStampedReference.compareAndSet(100, 101, stampHolder[0], stampHolder[0] + 1);
            System.out.println("Thread 2 - change value to 101: " + success);

            value = atomicStampedReference.get(stampHolder);
            System.out.println("Thread 2 - final value: " + value + ", final stamp: " + stampHolder[0]);
        });

        t1.start();
        t2.start();
    }
}

class SynchronizedExample {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }

    public static void main(String[] args) {
        SynchronizedExample example = new SynchronizedExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + example.getCount());
    }
}

class LockExample {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        LockExample example = new LockExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + example.getCount());
    }
}

class TryLockExample {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

    public void increment() {
        if (lock.tryLock()) {
            try {
                count++;
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Could not acquire lock");
        }
    }

    public int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        TryLockExample example = new TryLockExample();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                example.increment();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final count: " + example.getCount());
    }
}

class LockInterruptiblyExample {
    private final Lock lock = new ReentrantLock();

    public void performTask() {
        try {
            lock.lockInterruptibly();
            try {
                System.out.println(Thread.currentThread().getName() + " acquired the lock.");
                // 模拟一些工作
                Thread.sleep(2000);
            } finally {
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " released the lock.");
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " was interrupted while waiting for the lock.");
        }
    }

    public static void main(String[] args) {
        LockInterruptiblyExample example = new LockInterruptiblyExample();
        Runnable task = example::performTask;

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();

        // 让主线程稍微等待一下，让 t1 和 t2 尝试获取锁
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 中断线程 t2
        t2.interrupt();
    }
}


class Test1 {
    public void test1() {
        for (int i = 0; i < 10000; i++) {
            synchronized (Test1.class) {
                System.out.println("hei");
            }
        }
    }

    public void test2() {
        synchronized (Test1.class) {
            for (int i = 0; i < 10000; i++) {
                System.out.println("hei");
            }
        }
    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();
        test1.test1();
        test1.test2();
    }
}

/**
 * 内存溢出模拟
 */
class MemoryOverflowExample {
    // 存储分配的字节数组，防止被垃圾回收
    public static List<byte[]> byteList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            // 不断分配内存并存储在列表中
            for (int i = 0; ; i++) {
                byte[] bytes = new byte[1024 * 1024 * 10]; // 分配 10MB 的内存
                byteList.add(bytes); // 将分配的内存存储在列表中
                System.out.println("Allocated " + (i + 1) + " chunks of memory");
            }
        } catch (OutOfMemoryError e) {
            // 捕获内存溢出异常并打印
            System.err.println("Memory overflow occurred!");
            e.printStackTrace();
        }
    }
}


class SemaphoreExample {

    private static final Semaphore semaphore = new Semaphore(3); // 初始化信号量为0

    public static void main(String[] args) throws InterruptedException {
        // 启动多个线程来获取信号量
        for (int i = 0; i < 5; i++) {
            new Thread(new Worker(semaphore)).start();
        }

        // 主线程休眠2秒以确保所有工作线程都尝试获取信号量
        Thread.sleep(2000);

        // 启动一个线程释放许可
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " Releasing permit...");
            System.out.println(Thread.currentThread().getName() + ":" + semaphore.availablePermits());
            semaphore.release(); // 释放一个许可
            System.out.println(Thread.currentThread().getName() + ":" + semaphore.availablePermits());
        }).start();
    }

    static class Worker implements Runnable {
        private final Semaphore semaphore;

        Worker(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + " trying to acquire permit...");
                semaphore.acquire(); // 尝试获取许可
                System.out.println(Thread.currentThread().getName() + " acquired permit!");
                Thread.sleep(1000);
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + ":" + semaphore.availablePermits());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}


class ReadWriteLockExample {
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private int value;

    public int readValue() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " is reading." + value);
            return value;
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void writeValue(int value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " is writing.");
            this.value = value;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockExample example = new ReadWriteLockExample();

        Runnable readTask = () -> {
            for (int i = 0; i < 5; i++) {
                example.readValue();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        Runnable writeTask = () -> {
            for (int i = 0; i < 5; i++) {
                example.writeValue(i);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        Thread[] readers = new Thread[5];
        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Thread(readTask, "Reader-" + i);
            readers[i].start();
        }

        Thread writer = new Thread(writeTask, "Writer");
        writer.start();
    }
}

/**
 * 线程池例子
 */
class ThreadPoolExample {
    public static void main(String[] args) {
        // 创建一个固定大小为5的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        // 提交10个任务到线程池
        for (int i = 0; i < 10; i++) {
            int taskId = i;
            Runnable task = () -> {
                System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000); // 模拟任务执行时间
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " is completed on " + Thread.currentThread().getName());
            };
            executorService.submit(task);
        }

        // 优雅关闭线程池
        executorService.shutdown();
    }
}

class ThreadPoolExample1 {
    public static void main(String[] args) {
        // 创建线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,                     // corePoolSize
                10,                    // maximumPoolSize
                60, TimeUnit.SECONDS,  // keepAliveTime
                new LinkedBlockingQueue<>(18),  // workQueue
                Executors.defaultThreadFactory(),  // threadFactory
                new ThreadPoolExecutor.AbortPolicy()  // defaultHandler
        );

        // 提交任务
        for (int i = 0; i < 30; i++) {  // 提交30个任务
            final int taskId = i;
            threadPool.submit(() -> {
                System.out.println("Task " + taskId + " is running on " + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000); // 模拟任务执行时间
                } catch (Exception e) {
                    System.out.println("Task " + taskId + "is Error");
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task " + taskId + " is completed on " + Thread.currentThread().getName());
            });
        }

        // 关闭线程池
        threadPool.shutdown();
        try {
            // 等待所有任务完成
            if (!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Still waiting...");
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }
    }
}

class UnsafeDemo {
    private static final Unsafe unsafe;
//    private static final long valueOffset;
//    private volatile int value = 0;

    static {
        try {
            // 通过反射获取 Unsafe 实例
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            // 获取对象字段偏移量
//            valueOffset = unsafe.objectFieldOffset(bbb.UnsafeDemo.class.getDeclaredField("value"));
        } catch (Exception ex) {
            throw new Error(ex);
        }
    }

    public static void main(String[] args) {
        long address = unsafe.allocateMemory(1024);  // 分配1KB内存
        unsafe.putInt(address, 42);                  // 在该内存地址上写入数据
        int value = unsafe.getInt(address);          // 读取该内存地址的数据
        System.out.println(value);
        unsafe.freeMemory(address);
    }

//    public static void main(String[] args) {
//        bbb.UnsafeDemo example = new bbb.UnsafeDemo();
//        System.out.println("Initial value: " + example.value);
//
//        // 使用 CAS 修改值
//        boolean success = unsafe.compareAndSwapInt(example, valueOffset, 0, 42);
//        System.out.println("CAS success: " + success);
//        System.out.println("Updated value: " + example.value);
//    }
}

class BubbleSort {
    public static void main(String[] args) {
        int[] arr = {64, 34, 25, 12, 22, 11, 90};

        System.out.println("Original array:");
        printArray(arr);

        bubbleSort(arr);

        System.out.println("Sorted array:");
        printArray(arr);
    }

    static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    static void printArray(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}


class ExceptionHandling {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            // 提交任务并获取Future对象
            Future<?> future = executorService.submit(() -> {
                // 这里的代码会抛出异常
                throw new RuntimeException("任务中的异常");
            });

            // 在这里处理Future，尝试获取结果
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            // 捕获异常
            e.printStackTrace();
        } finally {
            // 关闭ExecutorService
            executorService.shutdown();
        }
    }
}

class MyClass {
    private int id;
    private String name;

    public MyClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void main(String[] args) {
        try {
            Class<MyClass> clazz = MyClass.class;
            Constructor<MyClass> constructor = clazz.getConstructor(int.class, String.class);
            MyClass myClassInstance = constructor.newInstance(1, "cxw");
            System.out.println(myClassInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class UserController {
    public String login(String username, String password) throws NoSuchAlgorithmException {
        String local = "123456";
        if (encryptPass(local).equals(encryptPass(password))) {
            System.out.println("生成token");
            return "success";
        } else {
            return "fail";
        }
    }

    private String encryptPass(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] digest = md5.digest(password.getBytes(StandardCharsets.UTF_8));
        String s = new BigInteger(1, digest).toString(16);
        System.out.println(s);
        System.out.println(s.length());
        return s;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        UserController userController = new UserController();
        System.out.println(userController.login("11111", "123456"));
    }
}

/**
 * Integer对象的比较
 */
class TestInteger {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        // 获取Unsafe实例
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        Integer i = 10;
        Integer j = 10;
        System.out.println(i == j);
        System.out.println(getObjectAddress(unsafe, i));
        System.out.println(getObjectAddress(unsafe, j));
        Integer a = 200;
        Integer b = 200;
        System.out.println(a == b);
        System.out.println(getObjectAddress(unsafe, a));
        System.out.println(getObjectAddress(unsafe, b));
    }

    private static long getObjectAddress(Unsafe unsafe, Object obj) {
        Object[] array = new Object[]{obj};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        return unsafe.getLong(array, baseOffset);
    }
}

interface FatherInterface {
    String hello();

    default String hello1() {
        return "";
    }

    private String hello2() {
        return "1";
    }
}

class Son implements FatherInterface {

    @Override
    public String hello() {
        return null;
    }

    @Override
    public String hello1() {
        return FatherInterface.super.hello1();
    }
}

class SunObj extends FatherObj {
    @Override
    public void aa() {
        super.aa();
    }

    @Override
    void bb() {
        super.bb();
    }

    @Override
    protected void cc() {
        super.cc();
    }
}

class TestObj {
    public static void main(String[] args) {
        FatherObj fatherObj = new FatherObj();
        fatherObj.aa();
        fatherObj.bb();
        fatherObj.cc();
    }
}

class TestString {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        // 获取Unsafe实例
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        // 使用“” 则会在字符串常量池中找，找到了则直接返回常量池中对象的引用
        String s1 = "aa";
        String s2 = "aa";
        System.out.println(s1 == s2);
        System.out.println("s1在常量池中的地址：" + getObjectAddress(unsafe, s1));
        System.out.println("s2在常量池中的地址：" + getObjectAddress(unsafe, s2));
        System.out.println(s1.equals(s2));
        System.out.println("============================");
        String s3 = new String("aa");
        System.out.println(s1 == s3);
        System.out.println(s1 == s3.intern());
        System.out.println("s1在常量池中的地址：" + getObjectAddress(unsafe, s1));
        System.out.println("s3在堆中的地址：" + getObjectAddress(unsafe, s3));
        // intern()会先前往字符串常量池中找，如果不在里面，则说明在堆中，就会把堆中对象的引用放进字符串常量池。
        System.out.println("s3在常量池中的地址，s3.intern()：" + getObjectAddress(unsafe, s3.intern()));
        s3 = s3.intern();
        System.out.println("s3拿到常量池中已存在的对象的引用，s3：" + getObjectAddress(unsafe, s3));
        System.out.println(s1.equals(s3));
        System.out.println("============================");
        String s4 = new String("bbbbb");
        System.out.println("s4未放进常量池：" + getObjectAddress(unsafe, s4));
        s4 = s4.intern();
        System.out.println("s4放进常量池：" + getObjectAddress(unsafe, s4));
        String s5 = "bbbbb";
        System.out.println("字符串常量s5：" + getObjectAddress(unsafe, s5));
        System.out.println(s4 == s5);
        System.out.println("============================");
        String s6 = new String("bbbbb");
        System.out.println("s6在堆中的引用地址：" + getObjectAddress(unsafe, s6));
        System.out.println(s4 == s6);
        System.out.println("s6在常量池中的引用地址,s6.intern()：" + getObjectAddress(unsafe, s6.intern()));
        System.out.println(s4 == s6.intern());
        System.out.println("============================");
        s6 = "bbbbb";
        System.out.println("s6在常量池中的地址：" + getObjectAddress(unsafe, s6));
        s6 = "bcd";
        System.out.println("s6在常量池中的地址：" + getObjectAddress(unsafe, s6));
        System.out.println("============================");
        String s7 = new String("abcd");
        System.out.println("s7在堆中的引用地址：" + getObjectAddress(unsafe, s7));
        String s8 = "abcd";
        System.out.println("字符串常量s8：" + getObjectAddress(unsafe, s8));
        System.out.println(s7 == s8);
        System.out.println(s7.intern() == s7);
        System.out.println(s7.intern() == s8);
        System.out.println(s8.intern() == s8);
        System.out.println(s7.equals(s8));
        StringBuilder stringBuilder = new StringBuilder();
        StringBuffer stringBuffer = new StringBuffer();
        HashMap hashMap = new HashMap();
        Hashtable hashtable = new Hashtable<>();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        Semaphore semaphore = new Semaphore(5);
    }

    private static long getObjectAddress(Unsafe unsafe, Object obj) {
        Object[] array = new Object[]{obj};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        return unsafe.getLong(array, baseOffset);
    }
}

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by Rafa³ Rybak 194184 on 2015-04-11.
 */
public class Concurrent {
    protected static int opA, opB, opC, resolved, workerA, workerB, workerC, consumerR;
    public static config conf = new config();
    protected static Random rand = new Random();


    /*
    mOpA - parameter A
    mOpB - parameter B
    mOpC - Operator

    This Class contains information about TasksToDo.
     */
    static class containerToDo {
        public int mOpA, mOpB, mOpC;

        public containerToDo(int opA, int opB, int opC) {
            mOpA = opA;
            mOpB = opB;
            mOpC = opC;
        }
    }
    /*
    mOpA - parameter A
    mOpB - parameter B
    mOpC - Operator
    mResolved - Resolved

    This Class contains information about Store;
     */
    static class store {
        public int mOpA, mOpB, mOpC, mResolved;

        public store(int opA, int opB, int opC, int resolved) {
            mOpA = opA;
            mOpB = opB;
            mOpC = opC;
            mResolved = resolved;
        }
    }

        /*
        Boss class, using concurent and blockig queue to manage the multitasking;
        */

    static class Boss implements Runnable {
        BlockingQueue<containerToDo> queue;

        Boss(BlockingQueue<containerToDo> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                while (true) {
                    while(queue.size() == conf.MAX){
                        Thread.sleep(1000);
                    }
                        opA = rand.nextInt(9);
                        opB = rand.nextInt(9);
                        opC = rand.nextInt(2);
                        queue.put(new containerToDo(opA, opB, opC));
                        System.out.println("Procedure 1: " + opA + " " + opB + " " + opC + " List size now:" + queue.size());
                        Thread.sleep(rand.nextInt(9)*conf.BOSS_SLEEP);


                }
            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }
    }
    /*
    Worker class, using concurent and blockig queue to manage the multitasking;
    */
    static class Worker implements Runnable {
        protected BlockingQueue<containerToDo> queue;
        protected BlockingQueue<store> storeQueue;
        protected int counter = 0;

        Worker(BlockingQueue<containerToDo> queue, BlockingQueue<store> storeQueue) {
            this.queue = queue;
            this.storeQueue = storeQueue;
        }

        public void run() {

            while (true) {
                if (storeQueue.size() == conf.STORE_MAX) {
                    return;
                } else{
                    try {
                        Thread.sleep(conf.WORKER_SLEEP);
                        containerToDo cont = queue.take();
                        workerA = cont.mOpA;
                        workerB = cont.mOpB;
                        workerC = cont.mOpC;


                        switch (workerC) {
                            case 0:
                                resolved = workerA + workerB;
                                System.out.println("Worker take: " + workerA + " + " + workerB + " = " + resolved);
                                storeQueue.put(new store(workerA, workerB, workerC, resolved));
                                break;
                            case 1:
                                resolved = workerA - workerB;
                                System.out.println("Worker take: " + workerA + " - " + workerB + " = " + resolved);
                                storeQueue.put(new store(workerA, workerB, workerC, resolved));
                                break;
                            case 2:
                                resolved = workerA * workerB;
                                System.out.println("Worker take: " + workerA + " * " + workerB + " = " + resolved);
                                storeQueue.put(new store(workerA, workerB, workerC, resolved));
                                break;

                        }
                        counter++;

                        if(counter == 10){
                            Thread.sleep(conf.WORKER_FREE_TIME);
                            counter = 0;
                        }


                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                System.out.println("Resolved current size: " + queue.size());
            }
        }
    }
    /*
    Consumer class, using concurent and blockig queue to manage the multitasking;
    */
    static class Consumer implements Runnable {
        protected BlockingQueue<store> storeQueue;

        Consumer(BlockingQueue<store> storeQueue) {
            this.storeQueue = storeQueue;
        }

        public void run() {

            while (true) {
                    try {
                        Thread.sleep(conf.CONSUMER_SLEEP);
                        store element = storeQueue.take();
                        consumerR = element.mResolved;

                        } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                System.err.println("Consumer Take: " + consumerR + " Current store size: " +storeQueue.size());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<containerToDo> myToDo = new LinkedBlockingQueue();
        BlockingQueue<store> myStore = new LinkedBlockingQueue();
        new Thread(new Boss(myToDo)).start();

        for(int i =0; i< conf.MAX_WORKERS; i++){
            new Thread(new Worker(myToDo, myStore)).start();
            Thread.sleep(conf.WORKERS_CHANGE);
        }

        for(int i  = 0; i < conf.MAX_CUSTOMERS; i++) {
            new Thread(new Consumer(myStore)).start();
        }
    }



}



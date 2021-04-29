package ltd.lezos;

import ltd.lezos.work.SomeDummyWork;
import ltd.lezos.work.SomeLazyWork;
import ltd.lezos.worker.Worker;

import static ltd.lezos.worker.WorkerState.*;

public class Factory {
    public static void main(String[] args) {
        System.out.println("Start-One");
        testOne();
        System.out.println("End-One");
        System.out.println("Start-Two");
        testTwo();
        System.out.println("End-Two");
        System.out.println("Start-Three");
        testThree();
        System.out.println("End-Three");
        System.out.println("Start-Four");
        testFour();
        System.out.println("End-Four");
        System.out.println("Start-Multiple");
        testMultipleWorkers();
        System.out.println("End-Multiple");
    }

    private static void testOne() {
        // Create one worker
        Worker<SomeDummyWork> worker = new Worker<>();
        worker.setState(INITIAL);
        worker.addWorkPackage(new SomeDummyWork(1));
        worker.setState(OPERATIONAL);
        new Factory().waitOneSecond();
        worker.setState(STOPPED);
    }

//    private static void testFailedOne() {
//        // Create one worker
//        Worker<SomeOtherWork> worker = new Worker<>();
//        worker.setState(INITIAL);
//        worker.addWorkPackage(new SomeOtherWork(1));
//        worker.setState(OPERATIONAL);
//        new Factory().waitOneSecond();
//        worker.setState(STOPPED);
//    }

    private static void testTwo() {
        // Create one worker
        Worker<SomeDummyWork> worker = new Worker<>();
        worker.setState(INITIAL);
        for(int i=0; i<10; i++) {
            worker.addWorkPackage(new SomeDummyWork(i));
        }
        worker.setState(OPERATIONAL);
        new Factory().waitOneSecond();
        worker.setState(STOPPED);
    }

    private static void testThree() {
        // Create one worker
        Worker<SomeLazyWork> worker = new Worker<>();
        worker.setState(INITIAL);
        for(int i=0; i<10; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
        worker.setState(OPERATIONAL);
        worker.setState(STOPPED);
    }

    private static void testFour() {
        // Create one worker
        Worker<SomeLazyWork> worker = new Worker<>();
        worker.setState(INITIAL);
        for(int i=0; i<5; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
        worker.setState(OPERATIONAL);
        for(int i=10; i<15; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
        worker.setState(STOPPED);
        for(int i=20; i<25; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
    }

    private static void testMultipleWorkers() {
        // Create three workers
        Worker<SomeLazyWork> worker1 = new Worker<>();
        worker1.setState(INITIAL);
        Worker<SomeLazyWork> worker2 = new Worker<>();
        worker2.setState(INITIAL);
        Worker<SomeLazyWork> worker3 = new Worker<>();
        worker3.setState(INITIAL);
        for(int i=100; i<199; i++) {
            worker1.addWorkPackage(new SomeLazyWork(i));
        }
        for(int i=200; i<299; i++) {
            worker2.addWorkPackage(new SomeLazyWork(i));
        }
        for(int i=300; i<399; i++) {
            worker3.addWorkPackage(new SomeLazyWork(i));
        }
        worker1.setState(OPERATIONAL);
        worker2.setState(OPERATIONAL);
        new Factory().waitOneSecond();
        worker3.setState(OPERATIONAL);
        new Factory().waitOneSecond();
        worker1.setState(STOPPED);
        worker2.setState(STOPPED);
        new Factory().waitOneSecond();
        worker3.setState(STOPPED);
    }

    private synchronized void waitOneSecond() {
        try {
            wait(1000);
        } catch (InterruptedException e) {
        }
    }
}

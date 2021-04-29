package ltd.lezos;

import ltd.lezos.work.SomeDummyWork;
import ltd.lezos.work.SomeHTTPWork;
import ltd.lezos.work.SomeLazyWork;
import ltd.lezos.worker.Worker;
import ltd.lezos.worker.WorkerCapacityReachedException;

import static ltd.lezos.worker.WorkerState.*;

public class Factory {
    public static void main(String[] args) {
        try {
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
            System.out.println("Start-Capacity Test");
            testCapacity();
            System.out.println("End-Capacity Test");
            System.out.println("Start-HTTP Test");
            testOneHTTPWorker();
            System.out.println("End-HTTP Test");
            System.out.println("Start-HTTP Multiple");
            testMultipleHTTPWorkers();
            System.out.println("End-HTTP Multiple");
        } catch (WorkerCapacityReachedException e) {
            System.out.println("FAILURE: WorkerCapacityReachedException in main!");
        }
    }

    private static void testOne() throws WorkerCapacityReachedException {
        // Create one worker
        Worker<SomeDummyWork> worker = new Worker<>();
        worker.setState(INITIAL);
        worker.addWorkPackage(new SomeDummyWork(1001));
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

    private static void testTwo() throws WorkerCapacityReachedException {
        // Create one worker
        Worker<SomeDummyWork> worker = new Worker<>();
        worker.setState(INITIAL);
        for(int i=2000; i<2010; i++) {
            worker.addWorkPackage(new SomeDummyWork(i));
        }
        worker.setState(OPERATIONAL);
        new Factory().waitOneSecond();
        worker.setState(STOPPED);
    }

    private static void testThree() throws WorkerCapacityReachedException {
        // Create one worker
        Worker<SomeLazyWork> worker = new Worker<>();
        worker.setState(INITIAL);
        for(int i=3000; i<3010; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
        worker.setState(OPERATIONAL);
        worker.setState(STOPPED);
    }

    private static void testFour() throws WorkerCapacityReachedException {
        // Create one worker
        Worker<SomeLazyWork> worker = new Worker<>();
        worker.setState(INITIAL);
        for(int i=4000; i<4005; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
        worker.setState(OPERATIONAL);
        for(int i=4010; i<4015; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
        worker.setState(STOPPED);
        for(int i=4020; i<4025; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
    }

    private static void testMultipleWorkers() throws WorkerCapacityReachedException {
        // Create three workers
        Worker<SomeLazyWork> worker1 = new Worker<>();
        worker1.setState(INITIAL);
        Worker<SomeLazyWork> worker2 = new Worker<>();
        worker2.setState(INITIAL);
        Worker<SomeLazyWork> worker3 = new Worker<>();
        worker3.setState(INITIAL);
        for(int i=5100; i<5199; i++) {
            worker1.addWorkPackage(new SomeLazyWork(i));
        }
        for(int i=5200; i<5299; i++) {
            worker2.addWorkPackage(new SomeLazyWork(i));
        }
        for(int i=5300; i<5399; i++) {
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

    private static void testCapacity() throws WorkerCapacityReachedException {
        // Create one worker
        Worker<SomeLazyWork> worker = new Worker<>();
        worker.setMaxCapacity(10);
        worker.setState(INITIAL);
        // Add ten tasks
        for(int i=6000; i<6010; i++) {
            worker.addWorkPackage(new SomeLazyWork(i));
        }
        // Start consumption
        worker.setState(OPERATIONAL);
        // Wait a little
        new Factory().waitOneSecond();
        // Add another task
        worker.addWorkPackage(new SomeLazyWork(11));
        // Wait a little
        new Factory().waitOneSecond();
        // Add twenty tasks
        try {
            for (int i=6010; i<6020; i++) {
                worker.addWorkPackage(new SomeLazyWork(i));
            }
        }catch (WorkerCapacityReachedException e) {
            System.out.println("WorkerCapacityReachedException: " + e.getMessage());
        } finally {
            worker.setState(STOPPED);
        }
    }

    private static void testOneHTTPWorker() throws WorkerCapacityReachedException {
        // Create one worker
        Worker<SomeHTTPWork> worker = new Worker<>();
        worker.setState(INITIAL);
        worker.addWorkPackage(new SomeHTTPWork(7001));
        worker.setState(OPERATIONAL);
        new Factory().waitOneSecond();
        worker.setState(STOPPED);
    }

    private static void testMultipleHTTPWorkers() throws WorkerCapacityReachedException {
        // Create three workers
        Worker<SomeHTTPWork> worker1 = new Worker<>();
        worker1.setState(INITIAL);
        Worker<SomeHTTPWork> worker2 = new Worker<>();
        worker2.setState(INITIAL);
        Worker<SomeHTTPWork> worker3 = new Worker<>();
        worker3.setState(INITIAL);
        for(int i=8000; i<8010; i++) {
            worker1.addWorkPackage(new SomeHTTPWork(i));
        }
        for(int i=8200; i<8210; i++) {
            worker2.addWorkPackage(new SomeHTTPWork(i));
        }
        for(int i=8300; i<8310; i++) {
            worker3.addWorkPackage(new SomeHTTPWork(i));
        }
        worker1.setState(OPERATIONAL);
        worker2.setState(OPERATIONAL);
        worker3.setState(OPERATIONAL);
        new Factory().waitOneSecond();
        worker1.setState(STOPPED);
        worker2.setState(STOPPED);
        worker3.setState(STOPPED);
    }

    private synchronized void waitOneSecond() {
        try {
            wait(1000);
        } catch (InterruptedException e) {
        }
    }
}

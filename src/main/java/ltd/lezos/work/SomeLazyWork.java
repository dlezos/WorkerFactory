package ltd.lezos.work;

import ltd.lezos.worker.ResourceCreator;

public class SomeLazyWork extends SomeWork {
    ResourceCreator creator;

    public SomeLazyWork() {
    }

    public SomeLazyWork(long l) {
        super(l);
    }

    public SomeLazyWork(ResourceCreator creator) {
        super(creator);
    }

    public void run() {
        waitSomeTime();
        System.out.println(id);
    }

    private synchronized void waitSomeTime() {
        try {
            wait((long) (Math.random()*1000));
        } catch (InterruptedException e) {
        }
    }
}

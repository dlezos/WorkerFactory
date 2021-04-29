package ltd.lezos.work;

import ltd.lezos.worker.ResourceCreator;

public class SomeOtherWork implements Runnable {
    ResourceCreator creator;
    String id;

    public SomeOtherWork() {
        id = getClass().getCanonicalName().concat("-").concat(Long.toString(System.currentTimeMillis()));
    }

    public SomeOtherWork(long l) {
        id = getClass().getCanonicalName().concat("-").concat(Long.toString(l));
    }

    public SomeOtherWork(ResourceCreator creator) {
        this.creator = creator;
    }

    public void run() {
        System.out.println(id);
    }
}

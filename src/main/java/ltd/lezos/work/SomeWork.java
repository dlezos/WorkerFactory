package ltd.lezos.work;

import ltd.lezos.worker.ResourceCreator;

public abstract class SomeWork implements Runnable {
    ResourceCreator creator;
    String id;

    public SomeWork() {
        id = getClass().getSimpleName().concat("-").concat(Long.toString(System.currentTimeMillis()));
    }

    public SomeWork(long l) {
        id = getClass().getSimpleName().concat("-").concat(Long.toString(l));
    }

    public SomeWork(ResourceCreator creator) {
        this.creator = creator;
    }

    abstract public void run();

    public String getId() {
        return id;
    }
}

package ltd.lezos.work;

import ltd.lezos.worker.ResourceCreator;

public class SomeDummyWork extends SomeWork {
    ResourceCreator creator;

    public SomeDummyWork() {
    }

    public SomeDummyWork(long l) {
        super(l);
    }

    public SomeDummyWork(ResourceCreator creator) {
        super(creator);
    }

    public void run() {
        System.out.println(id);
    }
}

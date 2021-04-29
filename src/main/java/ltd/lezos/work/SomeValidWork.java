package ltd.lezos.work;

import ltd.lezos.worker.ResourceCreator;

public class SomeValidWork extends SomeWork {
    ResourceCreator creator;

    public SomeValidWork() {
    }

    public SomeValidWork(long l) {
        super(l);
    }

    public SomeValidWork(ResourceCreator creator) {
        this.creator = creator;
    }

    public void run() {
        System.out.println(id);
    }
}

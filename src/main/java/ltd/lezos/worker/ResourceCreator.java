package ltd.lezos.worker;

abstract public class ResourceCreator<R> {
    public ResourceCreator() {

    }
    abstract public R createResource();
}

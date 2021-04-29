package ltd.lezos.worker;

public class WorkerCapacityReachedException extends Exception {
    public WorkerCapacityReachedException() {
    }

    public WorkerCapacityReachedException(String message) {
        super(message);
    }
}

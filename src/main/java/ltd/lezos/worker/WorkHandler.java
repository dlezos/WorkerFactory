package ltd.lezos.worker;

import java.util.List;

public interface WorkHandler<W> {

    boolean addWorkPackage(W work) throws WorkerCapacityReachedException;

    boolean addWorkPackages(List<W> tasks) throws WorkerCapacityReachedException;

    WorkerState getState();

    void setState(WorkerState newState);

    void setMaxCapacity(long maxCapacity);
}

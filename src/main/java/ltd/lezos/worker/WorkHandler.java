package ltd.lezos.worker;

import java.util.List;

public interface WorkHandler<W> {

    boolean addWorkPackage(W work);

    boolean addWorkPackages(List<W> tasks);

    WorkerState getState();

    void setState(WorkerState newState);
}

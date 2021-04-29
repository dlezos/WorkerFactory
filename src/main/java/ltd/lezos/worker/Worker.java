package ltd.lezos.worker;

import ltd.lezos.work.SomeWork;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static ltd.lezos.worker.WorkerState.*;

public class Worker<W extends SomeWork> implements WorkHandler<W> {

    private List<W> tasks = new LinkedList<W>();
    private WorkerState state = INITIAL;
    private ExecutorService executor;

    // Here we always accept work but in case of INITIAL or STOPPED we return false and let the caller
    // to decide if the work will be also sent to another worker
    public boolean addWorkPackage(W work) {
        if (state == OPERATIONAL) {
            executor.execute(work);
        } else {
            tasks.add(work);
        }
        return state == OPERATIONAL;
    }

    public boolean addWorkPackages(List<W> tasks) {
        tasks.stream().forEach(w -> addWorkPackage(w));
        return state == OPERATIONAL;
    }

    public WorkerState getState() {
        return state;
    }

    public void setState(WorkerState newState) {
        this.state = newState;
        switch (newState) {
            case INITIAL:
            case STOPPED:
                // We could be more graceful than this
                //tasks.addAll((Collection<? extends W>) executor.shutdownNow());
                if (executor != null) {
                    try {
                        executor.awaitTermination(5, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                    }
                    executor.shutdown();
                    // Not sure if the above also released the threads, so set it to null to allow the GC to release the threads (?)
                    executor = null;
                }
                break;
            case OPERATIONAL:
                //If needed create the executor/threads
                if(executor == null) {
                    // This should be customizable or even better decorated, certainly shouldn't use the magic number 3
                    executor = Executors.newFixedThreadPool(3);
                }
                List<W> existingTasks = tasks;
                tasks = new LinkedList<W>();
                existingTasks.stream().forEach(w -> executor.execute(w));
                existingTasks = null;
                break;
        }
    }
}

package ltd.lezos.worker;

import ltd.lezos.work.SomeWork;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import static ltd.lezos.worker.WorkerState.*;

public class Worker<W extends SomeWork> implements WorkHandler<W> {

    private BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<Runnable>();
    private WorkerState state = INITIAL;
    private ExecutorService executor;
    // Again this shouldn't be a magic number
    private long maxCapacity = 1000;

    // Here we always accept work but in case of INITIAL or STOPPED we return false and let the caller
    // to decide if the work will be also sent to another worker
    public boolean addWorkPackage(W work) throws WorkerCapacityReachedException {
        if (tasks.size() > maxCapacity) {
            throw new WorkerCapacityReachedException(work.getId());
        }
        if (state == OPERATIONAL && executor != null) {
            executor.execute(work);
        } else {
            tasks.add(work);
        }
        return state == OPERATIONAL;
    }

    // Here we might get the exception with the list partially committed which leaves us in state we don't want to be!
    public boolean addWorkPackages(List<W> tasks) throws WorkerCapacityReachedException  {
        for(W work:tasks) {
            addWorkPackage(work);
        }
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
                    // Changed with the use of an intermediate queue since in JAVA 11 the existing Runnable in tasks do not get immediately executed
                    BlockingQueue<Runnable> newTasks = new LinkedBlockingQueue<Runnable>();
                    // This should be customizable or even better decorated, certainly shouldn't use the magic number 3
                    executor = new ThreadPoolExecutor(3, 3, 60, TimeUnit.SECONDS, newTasks);
                    for(Runnable work: tasks) {
                        executor.execute(work);
                    }
                    tasks = newTasks;
                }
                //List<W> existingTasks = tasks;
                //tasks = new LinkedList<W>();
                //existingTasks.stream().forEach(w -> executor.execute(w));
                //existingTasks = null;
                break;
        }
    }

    @Override
    public void setMaxCapacity(long maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}

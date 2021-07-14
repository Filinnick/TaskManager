import java.util.Date;
import java.util.concurrent.Callable;

public class TaskCallables {
    static class AddTask implements Callable<Task> {
        private Task currentTask;

        public AddTask(Task task){
            this.currentTask = task;
        }
        @Override
        public Task call() throws Exception {
            System.out.println("Executed at:" + (new Date()));
            return currentTask;
        }

    }
}

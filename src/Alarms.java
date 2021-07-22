import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Alarms {
    ScheduledThreadPoolExecutor exe = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);

    List<Future<Task>> futures = new ArrayList<>();
    //pool for a schedule
    public Alarms() {
        exe.setRemoveOnCancelPolicy(true);
    }

    void makeAlarm(ArrayList<Task> tasks){

        for (Task task : tasks) {
            addAlarm(task);
        }

    }

    void addAlarm(Task task){

        LocalDateTime dateOfTask = LocalDateTime.ofInstant(task.getDateOfAlarm().toInstant(), ZoneId.systemDefault());
        TaskCallables.AddTask newTask = new TaskCallables.AddTask(task);
        FutureTask<Task> futureTask = new FutureTask<>(newTask);
        exe.schedule(
                futureTask, //Callable, тест, вместо запуска уведомления о задаче с кнопками "Завершнить" и "Отложить (25мин)"
                LocalDateTime.now().until(dateOfTask, ChronoUnit.SECONDS),
                TimeUnit.SECONDS);
        futures.add(futureTask);

    }

    void addAlarm(Task task, int index){

        LocalDateTime dateOfTask = LocalDateTime.ofInstant(task.getDateOfAlarm().toInstant(), ZoneId.systemDefault());
        TaskCallables.AddTask newTask = new TaskCallables.AddTask(task);
        FutureTask<Task> futureTask = new FutureTask<>(newTask);
        exe.schedule(
                futureTask, //Callable, тест, вместо запуска уведомления о задаче с кнопками "Завершнить" и "Отложить (25мин)"
                LocalDateTime.now().until(dateOfTask, ChronoUnit.SECONDS),
                TimeUnit.SECONDS);
        futures.set(index,futureTask);

    }

    void updateAlarm(Task task){
        int index = task.getId();
        futures.get(index-1).cancel(true);
        addAlarm(task,index-1);
    }

    void deleteAlarm(Task task){
        int index = task.getId();
        futures.get(index-1).cancel(true);
    }

}

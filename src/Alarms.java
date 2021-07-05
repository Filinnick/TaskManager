import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

//TODO добавить вызов доп окна с текстом задачи и кнопками "Завершнить" и "Отложить (25мин)"
public class Alarms {

    //pool for a schedule
    ScheduledExecutorService exe = Executors.newScheduledThreadPool(1);

    List<Future<Task>> futures = new ArrayList<>();

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

    void updateAlarm(Task task){
        int index = task.getId();
        futures.get(index-1).cancel(true);
        addAlarm(task);
    }

    void deleteAlarm(Task task){
        int index = task.getId();
        futures.get(index-1).cancel(true);
    }






/*



    void addAlarm(Task task){



    }
*/

}

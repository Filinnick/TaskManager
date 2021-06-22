import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//TODO добавить вызов доп окна с текстом задачи и кнопками "Завершнить" и "Отложить (25мин)"
//кнопка "Отложить (25мин)" откладывает задачу на одну помодорку (25мин) ScheduledExecutorService
public class Alarms {

    ScheduledExecutorService exe = Executors.newScheduledThreadPool(1);

    void makeAlarm(ArrayList<Task> tasks){

        for (Task task : tasks) {
            addAlarm(task);
        }

    }

    void addAlarm(Task task){

        LocalDateTime dateOfTask = LocalDateTime.ofInstant(task.getDateOfAlarm().toInstant(), ZoneId.systemDefault());
        exe.schedule(
                () -> System.out.println("Executed at:" + (new Date())),
                LocalDateTime.now().until(dateOfTask, ChronoUnit.SECONDS),
                TimeUnit.SECONDS);

    }

}

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Actions {

    public void saveTask(Task task, Journal journal) {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        int taskNumber = journal.getTasksNumber();

        String newTask = task.getName() + "#" + task.getBody() + "#" + dateFormat.format(task.getDateOfAlarm())
                + "#" + task.getContacts() + "#" + (taskNumber + 1) +"\n";
        try(FileWriter writer = new FileWriter(journal.getFilePath(), true))
        {
            writer.write(newTask);
            journal.setTasksNumber(taskNumber + 1);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void updateTask(Task task, Journal journal, String name, String body, Date dateOfAlarm, String contact) {

        ArrayList<Task> tasksList = journal.getTasks();
        tasksList.get(task.getId()-1).setName(name);
        tasksList.get(task.getId()-1).setBody(body);
        tasksList.get(task.getId()-1).setDateOfAlarm(dateOfAlarm);
        tasksList.get(task.getId()-1).setContacts(contact);
        journal.setTasks(tasksList);

    }

    public void deleteTask(Task task, Journal journal) {

        int id=task.getId();
        ArrayList<Task> tasks = journal.getTasks();
        tasks.remove(id-1);
        journal.setTasks(tasks);

    }
}
